package com.app.widgets.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ToggleView(
    @StringRes text: Int,
    checked: Boolean = true,
    onCheckedChange: () -> Unit,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Text(text = stringResource(id = text))
    Spacer(modifier = Modifier.weight(1f))
    Switch(
        checked = checked,
        onCheckedChange = { onCheckedChange() },
    )
}