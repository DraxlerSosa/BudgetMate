package com.budgetmate.app.ui.reports

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budgetmate.app.BudgetMateApp
import com.budgetmate.app.R
import com.budgetmate.app.databinding.FragmentReportsBinding
import com.budgetmate.app.ui.transactions.TransactionAdapter
import com.budgetmate.app.util.firstDayOfMonth
import com.budgetmate.app.util.lastDayOfMonth
import com.budgetmate.app.util.toReadableDate
import com.budgetmate.app.util.toZar
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportsViewModel by viewModels()
    private var userId = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTransactions.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            userId = (requireActivity().application as BudgetMateApp).sessionManager.loggedInUserId.first()
            viewModel.load(userId, firstDayOfMonth(), lastDayOfMonth())
        }
        setupDateButtons()
        observe()
    }

    private fun setupDateButtons() {
        binding.btnStartDate.text = firstDayOfMonth().toReadableDate()
        binding.btnEndDate.text   = lastDayOfMonth().toReadableDate()

        binding.btnStartDate.setOnClickListener { showDatePicker { date ->
            viewModel.startDate = date
            binding.btnStartDate.text = date.toReadableDate()
            reload()
        }}
        binding.btnEndDate.setOnClickListener { showDatePicker { date ->
            viewModel.endDate = date
            binding.btnEndDate.text = date.toReadableDate()
            reload()
        }}
    }

    private fun reload() {
        if (userId != -1) viewModel.load(userId, viewModel.startDate, viewModel.endDate)
    }

    private fun showDatePicker(onDate: (String) -> Unit) {
        val c = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            onDate("%04d-%02d-%02d".format(y, m + 1, d))
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun observe() {
        viewModel.transactions.observe(viewLifecycleOwner) { list ->
            binding.tvNoData.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            val catMap = viewModel.categoryMap.value ?: emptyMap()
            val adapter = TransactionAdapter(
                catMap,
                onPhotoClick = { uri ->
                    findNavController().navigate(
                        R.id.photoViewerFragment,
                        bundleOf("photoUri" to uri.toString())
                    )
                },
                onDeleteClick = { viewModel.deleteTransaction(it) }
            )
            binding.rvTransactions.adapter = adapter
            adapter.submitList(list)
        }
        viewModel.total.observe(viewLifecycleOwner) { total ->
            binding.tvTotal.text = getString(R.string.total_spent, total.toZar())
        }
        viewModel.categoryTotals.observe(viewLifecycleOwner) { totals ->
            if (totals.isEmpty()) { binding.pieChart.visibility = View.GONE; return@observe }
            binding.pieChart.visibility = View.VISIBLE
            val entries = totals.map { PieEntry(it.total.toFloat(), it.categoryName) }
            val colours = totals.map { android.graphics.Color.parseColor(it.colourHex) }
            val dataSet = PieDataSet(entries, "").apply {
                colors = colours
                valueTextSize = 11f
                valueTextColor = android.graphics.Color.WHITE
            }
            binding.pieChart.apply {
                data = PieData(dataSet)
                description.isEnabled = false
                legend.isEnabled = true
                setUsePercentValues(true)
                invalidate()
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
