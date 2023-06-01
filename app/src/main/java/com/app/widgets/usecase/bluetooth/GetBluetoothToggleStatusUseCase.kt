package com.app.widgets.usecase.bluetooth

import com.app.widgets.repository.IDatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBluetoothToggleStatusUseCase @Inject constructor(
    private val repository: IDatastoreRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.getBluetoothStatus()
}