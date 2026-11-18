package com.alexqueudot.xrplants.spatial.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.platform.LocalSpatialConfiguration
import androidx.xr.scenecore.scene
import com.alexqueudot.xrplants.R
import com.alexqueudot.xrplants.ui.component.ToggleButton

@Composable
fun ToggleSpaceButton(modifier: Modifier = Modifier) {
    if (!LocalSpatialConfiguration.current.hasXrSpatialFeature) {
        return
    }
    val session = LocalSession.current ?: return
    val isSpatialUiEnabled = LocalSpatialCapabilities.current.isSpatialUiEnabled
    if (isSpatialUiEnabled) {
        ToggleButton(
            iconRes = R.drawable.ic_home_space_switch,
            contentDescription = stringResource(id = R.string.switch_to_home_space),
            onClick = { session.scene.requestHomeSpace() },
            modifier = modifier,
        )
    } else {
        ToggleButton(
            iconRes = R.drawable.ic_full_space_switch,
            contentDescription = stringResource(id = R.string.switch_to_full_space),
            onClick = { session.scene.requestFullSpace() },
            modifier = modifier,
        )
    }
}