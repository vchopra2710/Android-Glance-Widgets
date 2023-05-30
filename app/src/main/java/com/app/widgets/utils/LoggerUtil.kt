package com.app.widgets.utils

import android.util.Log

const val TAG_PREFIX = "_dbg"

inline fun <reified T> T.logd(message: String) {
    Log.d("$TAG_PREFIX${T::class.java.simpleName}", message)
}

inline fun <reified T> T.loge(message: String, error: Exception) {
    Log.e("$TAG_PREFIX${T::class.java.simpleName}", message, error)
}

inline fun <reified T> T.logw(message: String) {
    Log.w("$TAG_PREFIX${T::class.java.simpleName}", message)
}

inline fun <reified T> T.logi(message: String) {
    Log.i("$TAG_PREFIX${T::class.java.simpleName}", message)
}