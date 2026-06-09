package com.example.financeapp.data.remote.dto

data class TransactionDto(
    val id: String,
    val description: String,
    val amount: Double,
    val date: String,
    val type: String
)

