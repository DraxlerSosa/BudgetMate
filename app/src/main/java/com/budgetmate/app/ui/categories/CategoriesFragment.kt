package com.budgetmate.app.ui.categories

import android.os.Bundle
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

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val userId = (requireActivity().application as BudgetMateApp).sessionManager.loggedInUserId.first()
            viewModel.load(userId)
        }
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.fabAddCategory.setOnClickListener {
            AddCategoryBottomSheet().show(parentFragmentManager, "ADD_CAT")
        }
        observe()
    }

    private fun observe() {
        viewModel.categories.observe(viewLifecycleOwner) { cats ->
            binding.rvCategories.adapter = CategoryAdapter(cats,
                onEditClick   = { AddCategoryBottomSheet.newInstance(it).show(parentFragmentManager, "EDIT_CAT") },
                onDeleteClick = { cat ->
                    if (cat.isDefault) binding.root.snack(getString(R.string.cannot_delete_default))
                    else viewModel.deleteCategory(cat)
                }
            )
        }
        viewModel.error.observe(viewLifecycleOwner) { msg ->
            msg ?: return@observe
            binding.root.snack(msg)
            viewModel.clearError()
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}