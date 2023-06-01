package com.app.widgets.ui.widgets.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.ToggleAirplane
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.ToggleBluetooth
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.ToggleFlashlight
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.ToggleWifi
import com.app.widgets.usecase.airplane.GetAirplaneToggleStatusUseCase
import com.app.widgets.usecase.airplane.UpdateAirplaneToggleStatusUseCase
import com.app.widgets.usecase.bluetooth.GetBluetoothToggleStatusUseCase
import com.app.widgets.usecase.bluetooth.UpdateBluetoothToggleStatusUseCase
import com.app.widgets.usecase.flashlight.GetFlashlightToggleStatusUseCase
import com.app.widgets.usecase.flashlight.UpdateFlashlightToggleStatusUseCase
import com.app.widgets.usecase.wifi.GetWifiToggleStatusUseCase
import com.app.widgets.usecase.wifi.UpdateWifiToggleStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WidgetIndexViewModel @Inject constructor(
    /*storing and retrieving wifi toggle state*/
    private val getWifiToggleStatusUseCase: GetWifiToggleStatusUseCase,
    private val updateWifiToggleStatusUseCase: UpdateWifiToggleStatusUseCase,

    /*storing and retrieving bluetooth toggle state*/
    private val getBluetoothToggleStatusUseCase: GetBluetoothToggleStatusUseCase,
    private val updateBluetoothToggleStatusUseCase: UpdateBluetoothToggleStatusUseCase,

    /*storing and retrieving airplane toggle state*/
    private val getAirplaneToggleStatusUseCase: GetAirplaneToggleStatusUseCase,
    private val updateAirplaneToggleStatusUseCase: UpdateAirplaneToggleStatusUseCase,

    /*storing and retrieving flashlight toggle state*/
    private val getFlashlightToggleStatusUseCase: GetFlashlightToggleStatusUseCase,
    private val updateFlashlightToggleStatusUseCase: UpdateFlashlightToggleStatusUseCase,

    ) : ViewModel() {
    private val _state = MutableStateFlow(WidgetIndexState())
    val state: StateFlow<WidgetIndexState>
        get() = _state

    init {
        getToggleStatus()
    }

    fun onEvent(event: WidgetIndexEvent) = when (event) {
        ToggleWifi -> toggleWifi()
        ToggleBluetooth -> toggleBluetooth()
        ToggleAirplane -> toggleAirplane()
        ToggleFlashlight -> toggleFlashlight()
    }

    private fun getToggleStatus() = viewModelScope.launch(Dispatchers.IO) {
        combine(
            getWifiToggleStatusUseCase(),
            getBluetoothToggleStatusUseCase(),
            getAirplaneToggleStatusUseCase(),
            getFlashlightToggleStatusUseCase()
        ) { wifiToggled, bluetoothToggled,
            airplaneToggled, flashlightToggled ->
            state.value.copy(
                wifiToggled = wifiToggled,
                bluetoothToggled = bluetoothToggled,
                airplaneToggled = airplaneToggled,
                flashlightToggled = flashlightToggled,
            )
        }.collectLatest { state ->
            _state.update {
                it.copy(
                    wifiToggled = state.wifiToggled,
                    bluetoothToggled = state.bluetoothToggled,
                    airplaneToggled = state.airplaneToggled,
                    flashlightToggled = state.flashlightToggled,
                )
            }
        }
    }

    private fun toggleWifi() = viewModelScope.launch {
        updateWifiToggleStatusUseCase(
            toggled = !state.value.wifiToggled
        )
    }

    private fun toggleBluetooth() = viewModelScope.launch {
        updateBluetoothToggleStatusUseCase(
            toggled = !state.value.bluetoothToggled
        )
    }

    private fun toggleAirplane() = viewModelScope.launch {
        updateAirplaneToggleStatusUseCase(
            toggled = !state.value.airplaneToggled
        )
    }

    private fun toggleFlashlight() = viewModelScope.launch {
        updateFlashlightToggleStatusUseCase(
            toggled = !state.value.flashlightToggled
        )
    }
}