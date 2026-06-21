package com.example.financeapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, senha: String): Result<Unit>
    suspend fun cadastrar(email: String, senha: String): Result<Unit>
    suspend fun recuperarSenha(email: String): Result<Unit>
    fun logout()
    fun isUsuarioLogado(): Boolean
    fun getUsuarioId(): String?
}