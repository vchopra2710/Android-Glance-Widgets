package com.app.widgets.usecase.bluetooth

import com.app.widgets.repository.IDatastoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateBluetoothToggleStatusUseCase @Inject constructor(
    private val repository: IDatastoreRepository,
) {
    suspend operator fun invoke(toggled: Boolean) {
        withContext(Dispatchers.IO) {
            repository.storeBluetoothStatus(toggled = toggled)
        }
    }
}