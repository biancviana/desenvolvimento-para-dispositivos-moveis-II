package com.example.financeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.financeapp.domain.repository.AuthRepository
import com.example.financeapp.ui.screens.home.HomeScreen
import com.example.financeapp.ui.screens.home.auth.AuthScreen

import com.example.financeapp.ui.theme.FinanceAppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val authRepository: AuthRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceAppTheme {
                var usuarioAutenticado by remember {
                    mutableStateOf(authRepository.isUsuarioLogado())
                }

                if (usuarioAutenticado) {
                    HomeScreen()
                } else {
                    AuthScreen(
                        onAuthSuccess = { usuarioAutenticado = true }
                    )
                }
            }
        }
    }
}