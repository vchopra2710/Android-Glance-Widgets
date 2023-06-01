package com.app.widgets.usecase.airplane

import com.app.widgets.repository.IDatastoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateAirplaneToggleStatusUseCase @Inject constructor(
    private val repository: IDatastoreRepository,
) {
    suspend operator fun invoke(toggled: Boolean) {
        withContext(Dispatchers.IO) {
            repository.storeAirplaneStatus(toggled = toggled)
        }
    }
}