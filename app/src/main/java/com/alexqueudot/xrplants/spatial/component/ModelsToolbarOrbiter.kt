package com.alexqueudot.xrplants.spatial.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Landscape
import androidx.compose.material.icons.rounded.Park
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterAnchorPoint
import androidx.xr.compose.spatial.OrbiterDefaults
import androidx.xr.compose.unit.DpVolumeOffset
import com.alexqueudot.xrplants.R

@Composable
internal fun ModelsToolbarOrbiter(
    isImmersiveMode: Boolean,
    onClearAllClick: () -> Unit,
    onToggleImmersiveModeClick: () -> Unit,
    onToggleEnvironmentClick: () -> Unit,
) {
    Orbiter(
        anchorPoint = OrbiterAnchorPoint.Top,
        offset = DpVolumeOffset(0.dp, 20.dp, OrbiterDefaults.Elevation),
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(50),
        ) {
            Row(modifier = Modifier.padding(12.dp)) {
                FilledTonalIconButton(onClick = onClearAllClick) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = stringResource(R.string.clear_all_models),
                    )
                }
                FilledTonalIconButton(onClick = onToggleImmersiveModeClick) {
                    Icon(
                        imageVector = if (isImmersiveMode) {
                            Icons.Rounded.GridView
                        } else {
                            Icons.Rounded.Park
                        },
                        contentDescription = stringResource(
                            if (isImmersiveMode) {
                                R.string.exit_immersive_mode
                            } else {
                                R.string.enter_immersive_mode
                            },
                        ),
                    )
                }
                FilledTonalIconButton(onClick = onToggleEnvironmentClick) {
                    Icon(
                        imageVector = Icons.Rounded.Landscape,
                        contentDescription = stringResource(R.string.toggle_environment),
                    )
                }
            }
        }
    }
}