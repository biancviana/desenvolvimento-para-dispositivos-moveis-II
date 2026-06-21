package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(): Flow<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(id: String)
    suspend fun updateTransaction(transaction: Transaction)
}