package com.budgetmate.app.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.budgetmate.app.BudgetMateApp
import com.budgetmate.app.data.entity.UserEntity
import com.budgetmate.app.data.repository.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for login and registration.
 * Exposes sealed AuthState so the UI never contains business logic.
 */
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    companion object { private const val TAG = "AuthViewModel" }

    private val app = application as BudgetMateApp
    private val repo = AuthRepository(app.database.userDao(), app.database.categoryDao())

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val user: UserEntity) : AuthState()
        object EmptyFields : AuthState()
        object PasswordTooShort : AuthState()
        object UsernameTaken : AuthState()
        object InvalidCredentials : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val _state = MutableLiveData<AuthState>(AuthState.Idle)
    val state: LiveData<AuthState> = _state

    fun login(username: String, password: String) {
        Log.d(TAG, "Login attempt: $username")
        _state.value = AuthState.Loading
        viewModelScope.launch {
            _state.value = when (val r = repo.login(username, password)) {
                is AuthRepository.AuthResult.Success -> {
                    app.sessionManager.setLoggedInUser(r.user.userId)
                    AuthState.Success(r.user)
                }
                AuthRepository.AuthResult.EmptyFields        -> AuthState.EmptyFields
                AuthRepository.AuthResult.InvalidCredentials -> AuthState.InvalidCredentials
                else -> AuthState.Error("Unexpected error")
            }
        }
    }

    fun register(username: String, password: String, displayName: String) {
        Log.d(TAG, "Register attempt: $username")
        _state.value = AuthState.Loading
        viewModelScope.launch {
            _state.value = when (val r = repo.register(username, password, displayName)) {
                is AuthRepository.AuthResult.Success -> {
                    app.sessionManager.setLoggedInUser(r.user.userId)
                    AuthState.Success(r.user)
                }
                AuthRepository.AuthResult.EmptyFields      -> AuthState.EmptyFields
                AuthRepository.AuthResult.PasswordTooShort -> AuthState.PasswordTooShort
                AuthRepository.AuthResult.UsernameTaken    -> AuthState.UsernameTaken
                else -> AuthState.Error("Unexpected error")
            }
        }
    }

    fun reset() { _state.value = AuthState.Idle }
}