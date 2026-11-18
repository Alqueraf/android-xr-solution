package com.alexqueudot.xrplants.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexqueudot.xrplants.R
import com.alexqueudot.xrplants.data.Plant
import com.alexqueudot.xrplants.data.Plants
import com.alexqueudot.xrplants.spatial.component.ToggleSpaceButton
import com.alexqueudot.xrplants.ui.component.CareScale
import com.alexqueudot.xrplants.ui.theme.PlantsTheme

@Composable
fun PlantListScreen(
    onPlantClick: (Plant) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                PlantListHeader(plantCount = Plants.all.size)
            }
            items(Plants.all, key = { plant -> plant.id }) { plant ->
                PlantListItem(
                    plant = plant,
                    onPlantClick = onPlantClick,
                )
            }
        }
    }
}

@Composable
fun PlantListHeader(plantCount: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Rounded.Eco,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.app_title),
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = stringResource(R.string.list_subtitle, plantCount),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        ToggleSpaceButton()
    }
}

@Composable
fun PlantListItem(
    plant: Plant,
    onPlantClick: (Plant) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onPlantClick(plant) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = plant.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(16.dp)),
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = plant.category.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = plant.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = plant.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                CareScale(
                    icon = Icons.Rounded.WbSunny,
                    label = stringResource(R.string.care_sun),
                    description = plant.sunNeeds.label,
                    level = plant.sunNeeds.level,
                )
                CareScale(
                    icon = Icons.Rounded.WaterDrop,
                    label = stringResource(R.string.care_water),
                    description = plant.waterNeeds.label,
                    level = plant.waterNeeds.level,
                )
            }
        }
    }
}

@Preview(name = "Light list", widthDp = 400, heightDp = 800)
@Preview(
    name = "Dark spatial list",
    widthDp = 1000,
    heightDp = 620,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Dark compact list",
    widthDp = 360,
    heightDp = 800,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun PlantListScreenPreview() {
    PlantsTheme {
        PlantListScreen(onPlantClick = {})
    }
}

@Preview(name = "Compact card", widthDp = 320, showBackground = true)
@Preview(name = "Large text card", widthDp = 320, fontScale = 1.5f, showBackground = true)
@Composable
private fun PlantListItemPreview() {
    PlantsTheme {
        PlantListItem(
            plant = Plants.all[1],
            onPlantClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
