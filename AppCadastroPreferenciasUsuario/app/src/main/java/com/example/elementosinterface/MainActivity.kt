package com.example.elementosinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elementosinterface.ui.theme.ElementosInterfaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElementosInterfaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Formulario(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(modifier: Modifier) {

    var nome by rememberSaveable { mutableStateOf("") }
    var tema by rememberSaveable { mutableStateOf("Claro") }
    var notificacoes by rememberSaveable { mutableStateOf(false) }
    var nivel by rememberSaveable { mutableStateOf(5f) }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        // Campo para nome do usuário
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do usuário") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Opção para escolher um tema do aplicativo
        Text("Tema do aplicativo")

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = tema == "Claro",
                onClick = { tema = "Claro" }
            )
            Text("Claro")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = tema == "Escuro",
                onClick = { tema = "Escuro" }
            )
            Text("Escuro")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Switch para ativar/desativar notificações
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ativar notificações")
            Switch(
                checked = notificacoes,
                onCheckedChange = { notificacoes = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Slider para definir o nível de experiência (0 a 10)
        Text("Nível de experiência: ${nivel.toInt()}")

        Slider(
            value = nivel,
            onValueChange = { nivel = it },
            valueRange = 0f..10f
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão "Salvar preferências" - a saída do click do botão vai imprimir os dados no console (logcat)
        Button(
            onClick = {
                println("Nome do usuário: $nome")
                println("Tema do app: $tema")
                println("Notificações: $notificacoes")
                println("Nível de experiência: ${nivel.toInt()}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Salvar preferências")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElementosInterfaceTheme {
        Greeting("Android")
    }
}