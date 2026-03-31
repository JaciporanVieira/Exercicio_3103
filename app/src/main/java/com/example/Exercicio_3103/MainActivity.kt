package com.example.Exercicio_3103

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instância do gerenciador de dados (DataStore)
        val themeManager = ThemePreferenceManager(applicationContext)

        setContent {
            // Instancia a ViewModel usando a Factory para passar o themeManager
            val viewModel: ThemeViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return ThemeViewModel(themeManager) as T
                }
            })

            // Coleta o estado do tema de forma segura ao ciclo de vida
            val selectedTheme by viewModel.themeState.collectAsStateWithLifecycle()

            // Lógica para decidir se o visual deve ser Escuro ou Claro
            val isDark = when (selectedTheme) {
                "LIGHT" -> false
                "DARK" -> true
                else -> isSystemInDarkTheme()
            }

            // O MaterialTheme reage automaticamente à variável isDark
            MaterialTheme(colorScheme = if (isDark) darkColorScheme() else lightColorScheme()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConfigScreen(
                        currentTheme = selectedTheme,
                        onThemeChange = { viewModel.changeTheme(it) },
                        isDark = isDark
                    )
                }
            }
        }
    }
}

@Composable
fun ConfigScreen(currentTheme: String, onThemeChange: (String) -> Unit, isDark: Boolean) {
    val context = LocalContext.current // Contexto necessário para o Toast

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Configurações", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Escolha o tema do aplicativo:")
        Spacer(modifier = Modifier.height(10.dp))

        val options = listOf("LIGHT", "DARK", "SYSTEM")

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (option == currentTheme),
                    onClick = { onThemeChange(option) }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Botão Reativo: Cor muda conforme o tema e exibe mensagem ao clicar
        Button(
            onClick = {
                Toast.makeText(context, "Tema atual: $currentTheme", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                // Roxo no Light (0xFF6200EE) e Verde Água no Dark (0xFF03DAC5)
                containerColor = if (isDark) Color(0xFF03DAC5) else Color(0xFF6200EE)
            )
        ) {
            Text(
                text = "Botão de Cor Reativa",
                color = if (isDark) Color.Black else Color.White
            )
        }
    }
}