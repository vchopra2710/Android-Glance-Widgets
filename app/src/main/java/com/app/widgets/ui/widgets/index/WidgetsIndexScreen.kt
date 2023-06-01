package com.app.widgets.ui.widgets.index

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.widgets.ui.compose.ToggleView
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetsIndexScreen(
    viewModel: WidgetIndexViewModel = hiltViewModel(),
) = Scaffold(
    modifier = Modifier.navigationBarsPadding(),
) { paddingValues ->
    val state by viewModel.state.collectAsState()

    WidgetIndex(
        paddingValues = paddingValues,
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun WidgetIndex(
    paddingValues: PaddingValues,
    state: WidgetIndexState,
    onEvent: (WidgetIndexEvent) -> Unit,
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
) {
    items(state.widgetIndexContent.size) {
        val widgetContent = state.widgetIndexContent[it]
        ToggleView(
            text = widgetContent.text,
            enabledIcon = widgetContent.enableIcon,
            disabledIcon = widgetContent.disableIcon,
            checked = when (widgetContent.action) {
                ToggleWifi -> state.wifiToggled
                ToggleBluetooth -> state.bluetoothToggled
                ToggleAirplane -> state.airplaneToggled
                ToggleFlashlight -> state.flashlightToggled
            },
            onCheckedChange = { onEvent(widgetContent.action) },
        )
    }
}