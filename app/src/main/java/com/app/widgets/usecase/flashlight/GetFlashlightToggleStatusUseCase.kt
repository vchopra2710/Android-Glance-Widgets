package com.app.widgets.usecase.flashlight

import com.app.widgets.repository.IDatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFlashlightToggleStatusUseCase @Inject constructor(
    private val repository: IDatastoreRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.getFlashlightStatus()
}