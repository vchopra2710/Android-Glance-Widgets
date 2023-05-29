package com.app.widgets.ui.widgets.flashlight

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.app.widgets.R
import com.app.widgets.ui.widgets.flashlight.FlashlightAction.Companion.FLASH_LIGHT_KEY

object FlashlightWidget : GlanceAppWidget() {

    val flashLightOnKey = booleanPreferencesKey("flashLightOnKey")

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val flashLightOn = currentState(flashLightOnKey)
            Content(isFlashLightOn = flashLightOn ?: false)
        }
    }

    @Composable
    private fun Content(
        isFlashLightOn: Boolean
    ) = Box(
        modifier = GlanceModifier
            .size(90.dp)
            .background(Color.Black.copy(alpha = 0.2f))
            .padding(8.dp),
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .clickable(
                    actionRunCallback(
                        callbackClass = FlashlightAction::class.java,
                        parameters = actionParametersOf(getClickTypeActionParameterKey() to isFlashLightOn.compareTo(false))
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                resId = if (isFlashLightOn) R.drawable.flashlight_on
                else R.drawable.flashlight_off,
            )
            Spacer(modifier = GlanceModifier.defaultWeight())
            Enabled(text = if (isFlashLightOn) "On" else "Off")
        }
    }


    @Composable
    private fun Icon(
        @DrawableRes resId: Int,
        tint: Color = Color.White
    ) = Image(
        provider = ImageProvider(resId),
        contentDescription = null,
        modifier = GlanceModifier.size(40.dp),
        colorFilter = ColorFilter.tint(ColorProvider(tint))
    )

    @Composable
    private fun Enabled(text: String) = Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            color = ColorProvider(Color.White),
            fontSize = 16.sp,
        ),
        maxLines = 1
    )

    fun getClickTypeActionParameterKey(): ActionParameters.Key<Int> {
        return ActionParameters.Key(FLASH_LIGHT_KEY)
    }
}