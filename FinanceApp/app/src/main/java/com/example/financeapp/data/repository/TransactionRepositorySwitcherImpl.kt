package com.example.financeapp.data.repository

import com.example.financeapp.domain.model.DataSourceType
import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.repository.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class TransactionRepositorySwitcherImpl(
    private val roomRepo: TransactionRepositoryRoomImpl,
    private val remoteRepo: TransactionRepositoryRemoteImpl,
    private val firebaseRepo: TransactionRepositoryFirebaseImpl,
    private val settings: SettingsRepository
) : TransactionRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val activeRepo: Flow<TransactionRepository> =
        settings.selectedDataSource.map { type ->
            when (type) {
                DataSourceType.ROOM -> roomRepo
                DataSourceType.API -> remoteRepo
                DataSourceType.FIREBASE -> firebaseRepo
            }
        }

    override fun getTransactions(): Flow<List<Transaction>> =
        activeRepo.flatMapLatest { repo ->
            repo.getTransactions()
        }

    override suspend fun insertTransaction(transaction: Transaction) {
        activeRepo.first().insertTransaction(transaction)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        activeRepo.first().updateTransaction(transaction)
    }

    override suspend fun deleteTransaction(id: String) {
        activeRepo.first().deleteTransaction(id)
    }
}