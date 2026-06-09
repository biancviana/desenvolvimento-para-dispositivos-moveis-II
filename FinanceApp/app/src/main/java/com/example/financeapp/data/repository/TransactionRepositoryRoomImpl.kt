package com.example.financeapp.data.repository

import com.example.financeapp.data.remote.api.TransactionApi
import com.example.financeapp.data.remote.mapper.toDomain
import com.example.financeapp.data.remote.mapper.toDto
import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.map

class TransactionRepositoryRoomImpl(
    private val api: TransactionApi
) : TransactionRepository {
    private val transactions =
        MutableStateFlow<List<Transaction>>(emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadTransactions()
        }
    }

    private suspend fun loadTransactions() {
        val response =
            api.getTransactions()
        transactions.value =
            response.map {
                it.toDomain()
            }
    }

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactions
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        api.insertTransaction(
            transaction.toDto()
        )

        loadTransactions()
    }

    override suspend fun deleteTransaction(id: String) {
        api.deleteTransaction(mapOf("id" to id))

        loadTransactions()
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        api.updateTransaction(transaction.toDto())
        loadTransactions()
    }


}