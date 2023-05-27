package com.app.widgets.ui.widgets.index

import androidx.lifecycle.ViewModel
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.EnableConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WidgetIndexViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(WidgetIndexState())
    val state: StateFlow<WidgetIndexState>
        get() = _state

    fun onEvent(event: WidgetIndexEvent) = when (event) {
        EnableConnectivity -> enableConnectivity()
    }

    private fun enableConnectivity() {
        val status = state.value.connectivityEnabled
        _state.update { it.copy(connectivityEnabled = !status) }
    }
}