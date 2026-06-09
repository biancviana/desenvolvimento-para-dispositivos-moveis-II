package com.example.financeapp.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.domain.model.DataSourceType
import com.example.financeapp.ui.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val currentSource by viewModel.currentSource.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text("Fonte de Dados")
        },

        text = {
            Column {

                Text(
                    text = "Origem atual: $currentSource"
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                DataSourceType.entries.forEach { source ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = currentSource == source,
                            onClick = {
                                viewModel.updateDataSource(source)
                            }
                        )

                        Text(source.name)
                    }
                }
            }
        },

        confirmButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Fechar")
            }
        }
    )
}