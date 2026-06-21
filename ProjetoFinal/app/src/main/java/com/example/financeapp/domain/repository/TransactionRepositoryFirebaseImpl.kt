package com.example.financeapp.domain.repository

import com.example.financeapp.data.firebase.dto.TransactionFirebaseDto
import com.example.financeapp.data.firebase.mapper.toDomain
import com.example.financeapp.data.firebase.mapper.toFirebaseDto
import com.example.financeapp.domain.model.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TransactionRepositoryFirebaseImpl(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) : TransactionRepository {

    private val collection = firestore.collection("transactions")

    override fun getTransactions(): Flow<List<Transaction>> = callbackFlow {
        val userId = authRepository.getUsuarioId() ?: ""

        if (userId.isEmpty()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = collection
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    if (error.code == FirebaseFirestoreException.Code.PERMISSION_DENIED) {
                        trySend(emptyList())
                        close()
                    } else {
                        close(error)
                    }
                    return@addSnapshotListener
                }

                val docs = snapshot?.toObjects(TransactionFirebaseDto::class.java) ?: emptyList()
                trySend(docs.map { it.toDomain() })
            }

        awaitClose { listener.remove() }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        val userId = authRepository.getUsuarioId() ?: ""
        val docRef = collection.document()

        val dto = transaction.copy(id = docRef.id, userId = userId).toFirebaseDto()

        docRef.set(dto).await()
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        val dto = transaction.toFirebaseDto()
        collection.document(transaction.id).set(dto).await()
    }

    override suspend fun deleteTransaction(id: String) {
        collection.document(id).delete().await()
    }
}