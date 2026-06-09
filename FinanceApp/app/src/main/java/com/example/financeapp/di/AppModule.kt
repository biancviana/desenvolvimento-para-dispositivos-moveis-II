package com.example.financeapp.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.financeapp.data.firebase.datasource.FirestoreDataSource
import com.example.financeapp.data.local.database.AppDatabase
import com.example.financeapp.data.remote.api.TransactionApi
import com.example.financeapp.data.repository.SettingsRepository
import com.example.financeapp.data.repository.TransactionRepositoryFirebaseImpl
import com.example.financeapp.data.repository.TransactionRepositoryRemoteImpl
import com.example.financeapp.data.repository.TransactionRepositoryRoomImpl
import com.example.financeapp.data.repository.TransactionRepositorySwitcherImpl
import com.example.financeapp.domain.repository.TransactionRepository
import com.example.financeapp.domain.usecase.*
import com.example.financeapp.ui.screens.home.HomeViewModel
import com.example.financeapp.ui.screens.settings.SettingsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val Context.dataStore by preferencesDataStore(name = "settings")

val appModule = module {

    // ROOM
    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, "finance_db"
        ).fallbackToDestructiveMigration().build()
    }

    single {
        get<AppDatabase>().transactionDao()
    }

    // OKHTTP
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>()).build()
    }

    // RETROFIT
    single {
        Retrofit.Builder().baseUrl("http://10.0.2.2/financeapp/").client(get())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single<TransactionApi> {
        get<Retrofit>().create(TransactionApi::class.java)
    }

    // DATASTORE
    single {
        SettingsRepository(androidContext().dataStore)
    }

    // FIREBASE
    single {
        FirestoreDataSource()
    }

    // REPOSITÓRIOS CONCRETOS
    single {
        TransactionRepositoryRoomImpl(get())
    }

    single {
        TransactionRepositoryRemoteImpl(get())
    }

    single {
        TransactionRepositoryFirebaseImpl(get())
    }

    // REPOSITÓRIO PRINCIPAL (SWITCHER)
    single<TransactionRepository> {
        TransactionRepositorySwitcherImpl(
            roomRepo = get<TransactionRepositoryRoomImpl>(),
            remoteRepo = get<TransactionRepositoryRemoteImpl>(),
            firebaseRepo = get<TransactionRepositoryFirebaseImpl>(),
            settings = get<SettingsRepository>()
        )
    }

    // USE CASES
    factory { GetTransactionsUseCase(get()) }
    factory { InsertTransactionsUseCase(get()) }
    factory { UpdateTransactionUseCase(get()) }
    factory { DeleteTransactionUseCase(get()) }

    factory { GetActiveDataSourceUseCase(get()) }
    factory { SetActiveDataSourceUseCase(get()) }

    // VIEWMODELS
    viewModel {
        HomeViewModel(
            get(), get(), get(), get()
        )
    }

    viewModel {
        SettingsViewModel(
            get(), get()
        )
    }
}