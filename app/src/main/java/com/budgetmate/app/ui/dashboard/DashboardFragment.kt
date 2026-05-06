package com.budgetmate.app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budgetmate.app.BudgetMateApp
import com.budgetmate.app.R
import com.budgetmate.app.databinding.FragmentDashboardBinding
import com.budgetmate.app.util.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** Home screen: monthly total, goal indicator, XP bar, streak counter. */
class DashboardFragment : Fragment() {

    companion object { private const val TAG = "DashboardFragment" }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private var userId = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            userId = (requireActivity().application as BudgetMateApp).sessionManager.loggedInUserId.first()
            if (userId != -1) viewModel.load(userId)
        }
        binding.fabAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_addTransaction)
        }
        observe()
    }

    private fun observe() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user ?: return@observe
            binding.tvGreeting.text = getString(R.string.greeting, user.displayName)
        }

        viewModel.monthlyTotal.observe(viewLifecycleOwner) { total ->
            binding.tvMonthlyTotal.text = total.toZar()
            updateGoal(total)
        }

        viewModel.xp.observe(viewLifecycleOwner) { xp ->
            binding.tvXpValue.text  = "$xp XP"
            binding.tvXpLevel.text  = xpLevel(xp)
            binding.progressXp.progress = (xpLevelProgress(xp) * 100).toInt()
        }

        viewModel.streak.observe(viewLifecycleOwner) { streak ->
            binding.tvStreak.text = resources.getQuantityString(R.plurals.streak_days, streak, streak)
        }
    }

    private fun updateGoal(total: Double) {
        val min = viewModel.minGoal.value ?: 0.0
        val max = viewModel.maxGoal.value ?: 0.0
        if (max <= 0.0) { binding.cardGoal.hide(); return }

        binding.cardGoal.show()
        binding.progressGoal.progress = ((total / max) * 100).toInt().coerceIn(0, 100)
        binding.tvGoalRange.text = getString(R.string.goal_range, min.toZar(), max.toZar())

        val (statusText, colour) = when {
            total > max        -> Pair(getString(R.string.goal_exceeded), requireContext().getColor(R.color.error_red))
            total > max * 0.85 -> Pair(getString(R.string.goal_warning),  requireContext().getColor(R.color.warning_amber))
            else               -> Pair(getString(R.string.goal_on_track), requireContext().getColor(R.color.teal_primary))
        }
        binding.tvGoalStatus.text = statusText
        binding.progressGoal.setIndicatorColor(colour)
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}