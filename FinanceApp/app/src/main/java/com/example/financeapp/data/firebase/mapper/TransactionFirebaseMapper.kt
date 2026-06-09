package com.example.financeapp.data.firebase.mapper

import com.example.financeapp.data.firebase.dto.TransactionFirebaseDto
import com.example.financeapp.domain.model.Transaction
import com.example.financeapp.domain.model.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

fun TransactionFirebaseDto.toDomain(): Transaction {
    return Transaction(
        id = id,
        description = description,
        amount = BigDecimal(amount.toString()),
        date = runCatching {
            LocalDateTime.parse(
                date,
                formatter
            )
        }.getOrDefault(LocalDateTime.now()),
        type = runCatching { TransactionType.valueOf(type) }.getOrDefault(TransactionType.EXPENSE)
    )
}

fun Transaction.toFirebaseDto(): TransactionFirebaseDto {
    return TransactionFirebaseDto(
        id = id,
        description = description,
        amount = amount.toDouble(),
        date = date.format(formatter),
        type = type.name
    )
}

