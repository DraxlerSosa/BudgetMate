package com.budgetmate.app.ui.reports

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
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

/**
 * Displays a filterable list of transactions and a pie chart broken down by category.
 * Users can select a custom date range using the start/end date buttons.
 * Uses MPAndroidChart for the pie chart visualisation.
 */
class ReportsFragment : Fragment() {

    companion object { private const val TAG = "ReportsFragment" }

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
        Log.d(TAG, "ReportsFragment started")

        binding.rvTransactions.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        // Load reports for the current month by default on first open
        lifecycleScope.launch {
            userId = (requireActivity().application as BudgetMateApp).sessionManager.loggedInUserId.first()
            Log.d(TAG, "Loading reports for userId=$userId from ${firstDayOfMonth()} to ${lastDayOfMonth()}")
            viewModel.load(userId, firstDayOfMonth(), lastDayOfMonth())
        }

        setupDateButtons()
        observe()
    }

    /**
     * Wires up the start and end date picker buttons.
     * When a date is selected, the report data is reloaded for the new range.
     */
    private fun setupDateButtons() {
        binding.btnStartDate.text = firstDayOfMonth().toReadableDate()
        binding.btnEndDate.text   = lastDayOfMonth().toReadableDate()

        binding.btnStartDate.setOnClickListener { showDatePicker { date ->
            Log.d(TAG, "Start date changed to $date")
            viewModel.startDate = date
            binding.btnStartDate.text = date.toReadableDate()
            reload()
        }}
        binding.btnEndDate.setOnClickListener { showDatePicker { date ->
            Log.d(TAG, "End date changed to $date")
            viewModel.endDate = date
            binding.btnEndDate.text = date.toReadableDate()
            reload()
        }}
    }

    /** Reloads data from the ViewModel using the currently selected date range. */
    private fun reload() {
        if (userId != -1) {
            Log.d(TAG, "Reloading reports: ${viewModel.startDate} → ${viewModel.endDate}")
            viewModel.load(userId, viewModel.startDate, viewModel.endDate)
        }
    }

    /** Opens a standard Android DatePickerDialog and returns the selected date as a string. */
    private fun showDatePicker(onDate: (String) -> Unit) {
        val c = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            onDate("%04d-%02d-%02d".format(y, m + 1, d))
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
    }

    /**
     * Observes all LiveData from the ViewModel:
     * - transactions: the filtered list shown in the RecyclerView
     * - total: the sum of all transactions in the selected range
     * - categoryTotals: used to build the pie chart
     */
    private fun observe() {
        viewModel.transactions.observe(viewLifecycleOwner) { list ->
            Log.d(TAG, "Transactions loaded: ${list.size} records")
            binding.tvNoData.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            val catMap = viewModel.categoryMap.value ?: emptyMap()
            val adapter = TransactionAdapter(
                catMap,
                onPhotoClick = { uri ->
                    Log.d(TAG, "Photo clicked: $uri")
                    findNavController().navigate(
                        R.id.photoViewerFragment,
                        bundleOf("photoUri" to uri.toString())
                    )
                },
                onDeleteClick = { t ->
                    Log.i(TAG, "Deleting transaction ID=${t.transactionId}")
                    viewModel.deleteTransaction(t)
                }
            )
            binding.rvTransactions.adapter = adapter
            adapter.submitList(list)
        }

        viewModel.total.observe(viewLifecycleOwner) { total ->
            Log.d(TAG, "Total for range: $total")
            binding.tvTotal.text = getString(R.string.total_spent, total.toZar())
        }

        viewModel.categoryTotals.observe(viewLifecycleOwner) { totals ->
            if (totals.isEmpty()) {
                Log.d(TAG, "No category totals — hiding pie chart")
                binding.pieChart.visibility = View.GONE
                return@observe
            }
            Log.d(TAG, "Building pie chart with ${totals.size} categories")

            // Build pie chart entries from category totals
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
                invalidate() // Redraw the chart with the new data
            }
            binding.pieChart.visibility = View.VISIBLE
        }
    }

    // Release binding reference to avoid memory leaks
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
