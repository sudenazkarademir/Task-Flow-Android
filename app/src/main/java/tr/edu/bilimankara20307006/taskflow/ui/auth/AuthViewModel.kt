package tr.edu.bilimankara20307006.taskflow.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import tr.edu.bilimankara20307006.taskflow.data.model.User

data class AuthState(
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)

class AuthViewModel : ViewModel() {
    
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                errorMessage = null
            )
            
            // Simulate authentication delay
            delay(1500)
            
            // Mock authentication - in real app this would be Firebase Auth
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(
                    uid = "mock_user_123",
                    email = email,
                    displayName = email.substringBefore("@")
                )
                
                _authState.value = _authState.value.copy(
                    isAuthenticated = true,
                    isLoading = false,
                    user = user,
                    errorMessage = null
                )
            } else {
                _authState.value = _authState.value.copy(
                    isAuthenticated = false,
                    isLoading = false,
                    user = null,
                    errorMessage = "E-posta ve şifre boş olamaz"
                )
            }
        }
    }
    
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                errorMessage = null
            )
            
            // Simulate registration delay
            delay(2000)
            
            // Mock registration
            if (email.isNotEmpty() && password.length >= 6) {
                val user = User(
                    uid = "new_user_${System.currentTimeMillis()}",
                    email = email,
                    displayName = email.substringBefore("@")
                )
                
                _authState.value = _authState.value.copy(
                    isAuthenticated = true,
                    isLoading = false,
                    user = user,
                    errorMessage = null
                )
            } else {
                _authState.value = _authState.value.copy(
                    isAuthenticated = false,
                    isLoading = false,
                    user = null,
                    errorMessage = if (password.length < 6) "Şifre en az 6 karakter olmalı" else "Geçersiz e-posta adresi"
                )
            }
        }
    }
    
    fun signOut() {
        _authState.value = AuthState()
    }
    
    fun clearError() {
        _authState.value = _authState.value.copy(errorMessage = null)
    }
}