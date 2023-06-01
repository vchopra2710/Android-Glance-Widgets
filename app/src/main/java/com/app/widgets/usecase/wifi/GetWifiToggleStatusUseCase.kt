package com.app.widgets.usecase.wifi

import com.app.widgets.repository.IDatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWifiToggleStatusUseCase @Inject constructor(
    private val repository: IDatastoreRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.getWifiStatus()
}