package com.app.widgets.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SingleEvent : SingleEventWithContent<Unit>() {
    suspend fun send() {
        send(Unit)
    }
}

open class SingleEventWithContent<T> {
    private val channel = Channel<T>(Channel.BUFFERED)
    private val flow = channel.receiveAsFlow()

    suspend fun send(value: T) {
        channel.send(value)
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun collect(onEvent: (T) -> Unit) {
        val lifecycleOwner = LocalLifecycleOwner.current

        LaunchedEffect(true) {
            flow.flowWithLifecycle(lifecycleOwner.lifecycle).collect {
                onEvent(it)
            }
        }
    }
}