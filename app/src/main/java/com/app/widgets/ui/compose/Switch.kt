package com.app.widgets.ui.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ToggleView(
    @StringRes text: Int,
    @DrawableRes enabledIcon: Int,
    @DrawableRes disabledIcon: Int,
    checked: Boolean,
    onCheckedChange: () -> Unit,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .padding(top = 32.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Icon(
        painter = painterResource(id = if (checked) enabledIcon else disabledIcon),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
        text = stringResource(id = text),
        fontSize = 16.sp,
    )
    Spacer(modifier = Modifier.weight(1f))
    Switch(
        checked = checked,
        onCheckedChange = { onCheckedChange() },
        modifier = Modifier.size(20.dp)
    )
}