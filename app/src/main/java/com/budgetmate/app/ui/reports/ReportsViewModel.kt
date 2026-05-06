package com.budgetmate.app.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.budgetmate.app.BudgetMateApp
import com.budgetmate.app.data.dao.CategoryTotal
import com.budgetmate.app.data.entity.CategoryEntity
import com.budgetmate.app.data.entity.TransactionEntity
import com.budgetmate.app.data.repository.TransactionRepository
import com.budgetmate.app.util.firstDayOfMonth
import com.budgetmate.app.util.lastDayOfMonth
import kotlinx.coroutines.launch

class ReportsViewModel(application: Application) : AndroidViewModel(application) {

    private val app  = application as BudgetMateApp
    private val repo = TransactionRepository(app.database.transactionDao())

    var startDate = firstDayOfMonth()
    var endDate   = lastDayOfMonth()

    private val _transactions   = MutableLiveData<List<TransactionEntity>>(emptyList())
    val transactions: LiveData<List<TransactionEntity>> = _transactions

    private val _categoryTotals = MutableLiveData<List<CategoryTotal>>(emptyList())
    val categoryTotals: LiveData<List<CategoryTotal>> = _categoryTotals

    private val _total          = MutableLiveData(0.0)
    val total: LiveData<Double> = _total

    private val _categoryMap    = MutableLiveData<Map<Int, CategoryEntity>>(emptyMap())
    val categoryMap: LiveData<Map<Int, CategoryEntity>> = _categoryMap

    fun load(userId: Int, start: String, end: String) {
        startDate = start; endDate = end
        viewModelScope.launch {
            // Build category map for the adapter
            val cats = app.database.categoryDao().getCategoriesForUser(userId)
            _categoryMap.value = cats.associateBy { it.categoryId }
        }
        viewModelScope.launch {
            repo.observeTransactions(userId, start, end).collect {
                _transactions.value = it
                _total.value = it.filter { t -> t.type == "EXPENSE" }.sumOf { t -> t.amount }
            }
        }
        viewModelScope.launch {
            repo.observeCategoryTotals(userId, start, end).collect { _categoryTotals.value = it }
        }
    }

    fun deleteTransaction(t: TransactionEntity) {
        viewModelScope.launch { repo.deleteTransaction(t) }
    }
}
