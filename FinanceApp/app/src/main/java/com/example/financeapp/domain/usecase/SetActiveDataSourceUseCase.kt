package com.example.financeapp.domain.usecase

import com.example.financeapp.data.repository.SettingsRepository
import com.example.financeapp.domain.model.DataSourceType

class SetActiveDataSourceUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(source: DataSourceType) {
        settingsRepository.saveDataSource(source)
    }
}

