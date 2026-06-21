package com.example.financeapp.ui.screens.home.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val erro: String? = null,
    val isSucesso: Boolean = false
)

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun login(email: String, senha: String) {
        _uiState.update { it.copy(isLoading = true, erro = null) }
        viewModelScope.launch {
            repository.login(email, senha)
                .onSuccess { _uiState.update { it.copy(isLoading = false, isSucesso = true) } }
                .onFailure { error -> _uiState.update { it.copy(isLoading = false, erro = error.localizedMessage) } }
        }
    }

    fun cadastrar(email: String, senha: String) {
        _uiState.update { it.copy(isLoading = true, erro = null) }
        viewModelScope.launch {
            repository.cadastrar(email, senha)
                .onSuccess { _uiState.update { it.copy(isLoading = false, isSucesso = true) } }
                .onFailure { error -> _uiState.update { it.copy(isLoading = false, erro = error.localizedMessage) } }
        }
    }

    fun resetSucesso() {
        _uiState.update { it.copy(isSucesso = false) }
    }
}