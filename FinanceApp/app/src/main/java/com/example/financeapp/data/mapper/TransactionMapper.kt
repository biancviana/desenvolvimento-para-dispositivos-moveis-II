package com.example.financeapp.data.mapper

import com.example.financeapp.data.local.entity.TransactionEntity
import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.model.TransactionType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        description = description,
        amount = amount.toBigDecimal(),
        date = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(date),
            ZoneId.systemDefault()
        ),
        type = TransactionType.valueOf(type)
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        description = description,
        amount = amount.toDouble(),
        date = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        type = type.name
    )
}