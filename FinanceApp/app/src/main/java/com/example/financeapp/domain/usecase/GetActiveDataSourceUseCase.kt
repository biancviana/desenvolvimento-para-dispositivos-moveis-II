package com.example.financeapp.domain.usecase


import com.example.financeapp.data.repository.SettingsRepository
import com.example.financeapp.domain.model.DataSourceType
import kotlinx.coroutines.flow.Flow

class GetActiveDataSourceUseCase(private val settingsRepository: SettingsRepository) {

    operator fun invoke(): Flow<DataSourceType> {
        return settingsRepository.selectedDataSource
    }
}