package com.example.financeapp.domain.usecase

import com.example.financeapp.domain.repository.TransactionRepository

class DeleteTransactionUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(id: String) {
        repository.deleteTransaction(id)
    }
}