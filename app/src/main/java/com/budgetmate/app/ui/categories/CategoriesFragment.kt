package com.budgetmate.app.ui.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.budgetmate.app.BudgetMateApp
import com.budgetmate.app.R
import com.budgetmate.app.databinding.FragmentCategoriesBinding
import com.budgetmate.app.util.snack
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Displays the user's transaction categories in a 2-column grid.
 * Allows adding new categories via a bottom sheet and deleting existing ones.
 * Default categories cannot be deleted to ensure data integrity.
 */
class CategoriesFragment : Fragment() {

    companion object { private const val TAG = "CategoriesFragment" }

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "CategoriesFragment started")

        // Retrieve the logged-in user's ID from the session, then load their categories
        lifecycleScope.launch {
            val userId = (requireActivity().application as BudgetMateApp).sessionManager.loggedInUserId.first()
            Log.d(TAG, "Loading categories for userId=$userId")
            viewModel.load(userId)
        }

        // Display categories in a 2-column grid layout
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)

        // Open the Add Category bottom sheet when the FAB is tapped
        binding.fabAddCategory.setOnClickListener {
            Log.d(TAG, "FAB tapped — opening AddCategoryBottomSheet")
            AddCategoryBottomSheet().show(parentFragmentManager, "ADD_CAT")
        }

        observe()
    }

    /**
     * Observes LiveData from the ViewModel and updates the UI accordingly.
     * Prevents deletion of default categories to protect app functionality.
     */
    private fun observe() {
        viewModel.categories.observe(viewLifecycleOwner) { cats ->
            Log.d(TAG, "Categories loaded: ${cats.size} items")
            binding.rvCategories.adapter = CategoryAdapter(cats,
                onEditClick = { cat ->
                    Log.d(TAG, "Edit clicked for category: ${cat.name}")
                    AddCategoryBottomSheet.newInstance(cat).show(parentFragmentManager, "EDIT_CAT")
                },
                onDeleteClick = { cat ->
                    if (cat.isDefault) {
                        // Inform the user that default categories are protected
                        Log.w(TAG, "Attempted to delete default category: ${cat.name}")
                        binding.root.snack(getString(R.string.cannot_delete_default))
                    } else {
                        Log.i(TAG, "Deleting category: ${cat.name}")
                        viewModel.deleteCategory(cat)
                    }
                }
            )
        }

        // Show any error messages from the ViewModel as a Snackbar
        viewModel.error.observe(viewLifecycleOwner) { msg ->
            msg ?: return@observe
            Log.e(TAG, "Error in CategoriesViewModel: $msg")
            binding.root.snack(msg)
            viewModel.clearError()
        }
    }

    // Clean up the binding reference when the view is destroyed to prevent memory leaks
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
