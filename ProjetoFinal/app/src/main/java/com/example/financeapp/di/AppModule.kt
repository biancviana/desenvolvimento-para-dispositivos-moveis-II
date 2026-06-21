package com.example.financeapp.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.financeapp.data.firebase.repository.AuthRepositoryImpl
import com.example.financeapp.data.firebase.repository.SettingsRepository
import com.example.financeapp.domain.repository.TransactionRepositoryFirebaseImpl
import com.example.financeapp.domain.repository.AuthRepository
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.domain.usecase.*
import com.example.financeapp.ui.screens.home.HomeViewModel
import com.example.financeapp.ui.screens.home.SettingsViewModel
import com.example.financeapp.ui.screens.home.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore(name = "settings_preferences")

val appModule = module {

    single { androidContext().dataStore }

    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<TransactionRepository> { TransactionRepositoryFirebaseImpl(get(), get()) }
    single { SettingsRepository(get()) }

    // Use Cases
    factory { GetTransactionsUseCase(get()) }
    factory { InsertTransactionsUseCase(get()) }
    factory { UpdateTransactionUseCase(get()) }
    factory { DeleteTransactionUseCase(get()) }

    // ViewModels
    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
}