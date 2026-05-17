package com.example.alertdialog

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AgendamentoState(

    val dataSelecionada: String = "",
    val horaSelecionada: String = "",
    val nomeUsuario: String = "",
    val notificacaoAtivada: Boolean = false,

    val mostrarDateDialog: Boolean = false,
    val mostrarTimeDialog: Boolean = false,
    val mostrarAlertDialog: Boolean = false
)

class AgendamentoViewModel : ViewModel() {

    private val _state = MutableStateFlow(AgendamentoState())
    val state = _state.asStateFlow()

    fun abrirDateDialog() {
        _state.value = _state.value.copy(mostrarDateDialog = true)
    }

    fun fecharDateDialog() {
        _state.value = _state.value.copy(mostrarDateDialog = false)
    }

    fun abrirTimeDialog() {
        _state.value = _state.value.copy(mostrarTimeDialog = true)
    }

    fun fecharTimeDialog() {
        _state.value = _state.value.copy(mostrarTimeDialog = false)
    }

    fun abrirAlertDialog() {
        _state.value = _state.value.copy(mostrarAlertDialog = true)
    }

    fun fecharAlertDialog() {
        _state.value = _state.value.copy(mostrarAlertDialog = false)
    }

    fun confirmarData(data: String) {
        _state.value = _state.value.copy(
            dataSelecionada = data,
            mostrarDateDialog = false
        )
    }

    fun confirmarHora(hora: String) {
        _state.value = _state.value.copy(
            horaSelecionada = hora,
            mostrarTimeDialog = false
        )
    }

    fun confirmarDadosAlert(nome: String, notificacao: Boolean) {
        _state.value = _state.value.copy(
            nomeUsuario = nome,
            notificacaoAtivada = notificacao,
            mostrarAlertDialog = false
        )
    }

    fun limparAgendamento() {
        _state.value = AgendamentoState()
    }
}