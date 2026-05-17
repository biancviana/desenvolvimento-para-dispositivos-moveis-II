package com.example.alertdialog

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendamentoScreen(
    modifier: Modifier = Modifier,
    viewModel: AgendamentoViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    if (state.mostrarAlertDialog) {
        AlertDialogConfirmacao(
            onConfirmar = { nome, notificacao ->
                viewModel.confirmarDadosAlert(nome, notificacao)
            },
            onDismiss = { viewModel.fecharAlertDialog() }
        )
    }

    if (state.mostrarDateDialog) {
        DatePickerDialog(
            onConfirmar = { data -> viewModel.confirmarData(data) },
            onDismiss = { viewModel.fecharDateDialog() }
        )
    }

    if (state.mostrarTimeDialog) {
        TimePickerDialog(
            onConfirmar = { hora -> viewModel.confirmarHora(hora) },
            onDismiss = { viewModel.fecharTimeDialog() }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Agendamento",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider()

        OutlinedButton(
            onClick = { viewModel.abrirAlertDialog() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Informar dados do paciente")
        }

        OutlinedButton(
            onClick = { viewModel.abrirDateDialog() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Selecionar data")
        }

        OutlinedButton(
            onClick = { viewModel.abrirTimeDialog() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Selecionar horário")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Resumo",
                )

                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(
                    label = "Paciente:",
                    valor = state.nomeUsuario.ifBlank { "Não informado" }
                )

                InfoRow(
                    label = "Notificações:",
                    valor = if (state.notificacaoAtivada) "Ativadas" else "Desativadas"
                )

                InfoRow(
                    label = "Data:",
                    valor = state.dataSelecionada.ifBlank { "Não selecionada" }
                )

                InfoRow(
                    label = "Horário:",
                    valor = state.horaSelecionada.ifBlank { "Não selecionado" }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Consulta agendada para ${state.dataSelecionada} às ${state.horaSelecionada}!",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.limparAgendamento()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar agendamento")
        }
    }
}

@Composable
private fun InfoRow(label: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}