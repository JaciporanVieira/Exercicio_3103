package com.example.Exercicio_3103

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val repository: ThemePreferenceManager) : ViewModel() {

    // Transforma o Flow do DataStore em um StateFlow
    val themeState: StateFlow<String> = repository.themeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "SYSTEM"
    )

    // Função para atualizar o tema no DataStore
    fun changeTheme(newTheme: String) {
        viewModelScope.launch {
            repository.saveTheme(newTheme)
        }
    }
}