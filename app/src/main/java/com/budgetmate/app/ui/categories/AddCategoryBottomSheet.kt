package com.budgetmate.app.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.budgetmate.app.R
import com.budgetmate.app.data.entity.CategoryEntity
import com.budgetmate.app.databinding.FragmentAddCategoryBinding
import com.budgetmate.app.util.snack
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddCategoryBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_CATEGORY_ID   = "categoryId"
        private const val ARG_CATEGORY_NAME = "categoryName"
        private const val ARG_EMOJI         = "emoji"
        private const val ARG_COLOUR        = "colour"
        private const val ARG_CAP           = "cap"

        fun newInstance(cat: CategoryEntity) = AddCategoryBottomSheet().apply {
            arguments = bundleOf(
                ARG_CATEGORY_ID   to cat.categoryId,
                ARG_CATEGORY_NAME to cat.name,
                ARG_EMOJI         to cat.iconEmoji,
                ARG_COLOUR        to cat.colourHex,
                ARG_CAP           to (cat.monthlyBudgetCap ?: -1.0)
            )
        }
    }

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoriesViewModel by viewModels({ requireParentFragment() })

    // Editing an existing category when categoryId > 0
    private var editingCategory: CategoryEntity? = null

    private val emojiOptions = listOf("💰","🛒","🚗","💡","🎬","🍽️","❤️","🛍️","📚","📌","✈️","🏠","🐾","💻","🎵")
    private val colourOptions = listOf(
        "#00C9A7","#00B4D8","#FFB703","#8338EC",
        "#FB5607","#E63946","#F72585","#2DC653","#3A86FF","#6C757D"
    )
    private var selectedEmoji  = "💰"
    private var selectedColour = "#00C9A7"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pre-fill if editing
        val catId = arguments?.getInt(ARG_CATEGORY_ID, 0) ?: 0
        if (catId > 0) {
            binding.etCategoryName.setText(arguments?.getString(ARG_CATEGORY_NAME) ?: "")
            selectedEmoji  = arguments?.getString(ARG_EMOJI)  ?: "💰"
            selectedColour = arguments?.getString(ARG_COLOUR) ?: "#00C9A7"
            val cap = arguments?.getDouble(ARG_CAP, -1.0) ?: -1.0
            if (cap > 0) binding.etCap.setText(cap.toString())
            binding.tvSelectedEmoji.text = selectedEmoji
            binding.btnSaveCategory.text = getString(R.string.save)
        }

        setupEmojiChips()
        setupColourChips()

        binding.btnSaveCategory.setOnClickListener {
            val name = binding.etCategoryName.text.toString().trim()
            val capText = binding.etCap.text.toString().trim()
            val cap = if (capText.isBlank()) null else capText.toDoubleOrNull()

            if (catId > 0) {
                // Build a stub entity with the id so ViewModel can update
                viewModel.updateCategory(
                    CategoryEntity(
                        categoryId = catId,
                        userId = 0, // ViewModel fills real userId
                        name = name,
                        iconEmoji = selectedEmoji,
                        colourHex = selectedColour,
                        monthlyBudgetCap = cap
                    ),
                    name, selectedEmoji, selectedColour, cap
                )
            } else {
                viewModel.addCategory(name, selectedEmoji, selectedColour, cap)
            }
            dismiss()
        }
    }

    private fun setupEmojiChips() {
        binding.tvSelectedEmoji.text = selectedEmoji
        binding.chipGroupEmoji.removeAllViews()
        emojiOptions.forEach { emoji ->
            val chip = com.google.android.material.chip.Chip(requireContext()).apply {
                text = emoji
                isCheckable = true
                isChecked = (emoji == selectedEmoji)
                setOnClickListener {
                    selectedEmoji = emoji
                    binding.tvSelectedEmoji.text = emoji
                }
            }
            binding.chipGroupEmoji.addView(chip)
        }
    }

    private fun setupColourChips() {
        binding.chipGroupColour.removeAllViews()
        colourOptions.forEach { hex ->
            val chip = com.google.android.material.chip.Chip(requireContext()).apply {
                text = " "
                isCheckable = true
                isChecked = (hex == selectedColour)
                chipBackgroundColor = android.content.res.ColorStateList.valueOf(
                    android.graphics.Color.parseColor(hex)
                )
                setOnClickListener { selectedColour = hex }
            }
            binding.chipGroupColour.addView(chip)
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
