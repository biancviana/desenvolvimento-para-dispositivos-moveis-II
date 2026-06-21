package com.example.financeapp.data.firebase.repository

import com.example.financeapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override suspend fun login(email: String, senha: String): Result<Unit> = runCatching {
        firebaseAuth.signInWithEmailAndPassword(email, senha).await()
        Unit
    }

    override suspend fun cadastrar(email: String, senha: String): Result<Unit> = runCatching {
        firebaseAuth.createUserWithEmailAndPassword(email, senha).await()
        Unit
    }

    override suspend fun recuperarSenha(email: String): Result<Unit> = runCatching {
        firebaseAuth.sendPasswordResetEmail(email).await()
        Unit
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun isUsuarioLogado(): Boolean = firebaseAuth.currentUser != null

    override fun getUsuarioId(): String? = firebaseAuth.currentUser?.uid
}