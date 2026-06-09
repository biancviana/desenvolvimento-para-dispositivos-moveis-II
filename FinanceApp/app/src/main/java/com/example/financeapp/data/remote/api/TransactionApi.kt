package com.example.financeapp.data.remote.api

import com.example.financeapp.data.remote.dto.TransactionDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface TransactionApi {
    @GET("transactions.php")
    suspend fun getTransactions(): List<TransactionDto>

    @POST("insert_transaction.php")
    suspend fun insertTransaction(@Body transaction: TransactionDto)

    @PUT("update_transaction.php")
    suspend fun updateTransaction(
        @Body tansaction: TransactionDto
    )

    @HTTP(method = "DELETE", path = "delete_transaction.php", hasBody = true)
    suspend fun deleteTransaction(
        @Body body: Map<String, String>
    )
}

