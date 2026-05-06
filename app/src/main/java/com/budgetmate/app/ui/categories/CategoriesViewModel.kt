package com.budgetmate.app.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.budgetmate.app.BudgetMateApp
import com.budgetmate.app.data.entity.CategoryEntity
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    companion object { private const val TAG = "CategoriesViewModel" }

    private val app = application as BudgetMateApp
    private var userId = -1

    private val _categories  = MutableLiveData<List<CategoryEntity>>(emptyList())
    val categories: LiveData<List<CategoryEntity>> = _categories

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun load(userId: Int) {
        this.userId = userId
        viewModelScope.launch {
            app.database.categoryDao().observeCategoriesForUser(userId).collect {
                _categories.value = it
            }
        }
    }

    fun addCategory(name: String, emoji: String, colour: String, cap: Double?) {
        if (name.isBlank()) { _error.value = "Name cannot be empty"; return }
        if (name.length > 30) { _error.value = "Name must be 30 characters or less"; return }
        viewModelScope.launch {
            if (app.database.categoryDao().countByName(userId, name.trim()) > 0) {
                _error.value = "'${name.trim()}' already exists"; return@launch
            }
            app.database.categoryDao().insertCategory(
                CategoryEntity(userId = userId, name = name.trim(),
                    iconEmoji = emoji, colourHex = colour, monthlyBudgetCap = cap)
            )
            Log.i(TAG, "Category added: $name")
        }
    }

    fun updateCategory(cat: CategoryEntity, name: String, emoji: String, colour: String, cap: Double?) {
        viewModelScope.launch {
            app.database.categoryDao().updateCategory(
                cat.copy(name = name.trim(), iconEmoji = emoji, colourHex = colour, monthlyBudgetCap = cap)
            )
            Log.i(TAG, "Category updated: $name")
        }
    }

    fun deleteCategory(cat: CategoryEntity) {
        viewModelScope.launch {
            val count = app.database.categoryDao().transactionCountForCategory(cat.categoryId)
            if (count > 0) {
                _error.value = "Cannot delete '${cat.name}' — reassign its $count transaction(s) first"
            } else {
                app.database.categoryDao().deleteCategory(cat)
                Log.i(TAG, "Category deleted: ${cat.name}")
            }
        }
    }

    fun clearError() { _error.value = null }
}