package com.example.financeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.financeapp.data.local.dao.TransactionDao
import com.example.financeapp.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}

