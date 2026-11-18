package com.alexqueudot.xrplants.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.alexqueudot.xrplants.R

@Composable
fun CareScale(
    icon: ImageVector,
    label: String,
    description: String,
    level: Int,
    modifier: Modifier = Modifier,
) {
    val accessibilityLabel = stringResource(R.string.care_level_description, description, level)
    Row(
        modifier = modifier.clearAndSetSemantics {
            contentDescription = accessibilityLabel
        },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = 4.dp),
        )
        repeat(3) { index ->
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (index < level) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
                },
                modifier = Modifier.size(18.dp),
            )
        }
    }
}