package com.example.financeapp.data.repository

import com.example.financeapp.domain.model.DataSourceType
import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.repository.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class TransactionRepositoryProxy(
    private val roomImpl: TransactionRepositoryRoomImpl,
    private val remoteImpl: TransactionRepositoryRemoteImpl,
    private val firebaseImpl: TransactionRepositoryFirebaseImpl,
    private val settings: SettingsRepository
) : TransactionRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val activeRepository: Flow<TransactionRepository> = settings.selectedDataSource.map { type ->
        when (type) {
            DataSourceType.ROOM -> roomImpl
            DataSourceType.API -> remoteImpl
            DataSourceType.FIREBASE -> firebaseImpl
        }
    }

    override fun getTransactions(): Flow<List<Transaction>> {
        return activeRepository.flatMapLatest { it.getTransactions() }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        activeRepository.first().insertTransaction(transaction)
    }

    override suspend fun deleteTransaction(id: String) {
        activeRepository.first().deleteTransaction(id)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        activeRepository.first().updateTransaction(transaction)
    }
}