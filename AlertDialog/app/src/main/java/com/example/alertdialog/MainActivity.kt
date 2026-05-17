package com.example.alertdialog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.alertdialog.ui.theme.AlertDialogTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlertDialogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AgendamentoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}