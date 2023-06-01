package com.app.widgets.usecase.airplane

import com.app.widgets.repository.IDatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAirplaneToggleStatusUseCase @Inject constructor(
    private val repository: IDatastoreRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.getAirplaneStatus()
}