package com.alexqueudot.xrplants.spatial

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.offset
import androidx.xr.compose.subspace.layout.requiredWidthIn
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import androidx.xr.scenecore.scene
import com.alexqueudot.xrplants.data.Plant
import com.alexqueudot.xrplants.environment.GardenEnvironment
import com.alexqueudot.xrplants.spatial.component.ModelsToolbarOrbiter
import com.alexqueudot.xrplants.spatial.component.Plant3dModel
import com.alexqueudot.xrplants.spatial.component.Plant3dModelInstance
import com.alexqueudot.xrplants.ui.screen.PlantDetailScreen
import com.alexqueudot.xrplants.ui.screen.PlantListScreen
import com.alexqueudot.xrplants.ui.theme.PlantsTheme
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicLong

@Composable
fun SpatialPlantsApp() {
    val selectedPlants = remember { mutableStateListOf<Plant>() }
    val modelInstances = remember { mutableStateListOf<Plant3dModelInstance>() }
    val instanceIdCounter = remember { AtomicLong(0) }
    var isImmersiveMode by remember { mutableStateOf(false) }
    var currentEnvironment by remember { mutableStateOf(GardenEnvironment.DEFAULT) }
    Subspace(
        modifier = SubspaceModifier.requiredWidthIn(max = 4000.dp),
    ) {
        val session = checkNotNull(LocalSession.current)
        val scope = rememberCoroutineScope()
        val toggleEnvironment: () -> Unit = {
            scope.launch {
                val nextEnvironment = currentEnvironment.next()
                currentEnvironment = nextEnvironment
                session.scene.spatialEnvironment.preferredSpatialEnvironment =
                    nextEnvironment.toSpatialEnvironmentPreference(session)
            }
        }
        if (!isImmersiveMode) {
            SpatialPanel(
                modifier = SubspaceModifier
                    .width(700.dp)
                    .height(1000.dp),
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PlantListScreen(
                        onPlantClick = { plant ->
                            if (selectedPlants.none { selected -> selected.id == plant.id }) {
                                selectedPlants.add(plant)
                            }
                        },
                    )
                }
                ModelsToolbarOrbiter(
                    isImmersiveMode = isImmersiveMode,
                    onClearAllClick = {
                        modelInstances.clear()
                    },
                    onToggleImmersiveModeClick = { isImmersiveMode = true },
                    onToggleEnvironmentClick = toggleEnvironment,
                )
            }
            selectedPlants.forEachIndexed { index, plant ->
                key("panel-${plant.id}") {
                    SpatialPanel(
                        modifier = SubspaceModifier
                            .width(520.dp)
                            .height(630.dp)
                            .offset(
                                x = 340.dp + (index * 200).dp,
                                y = 50.dp,
                                z = 150.dp + (index * 100).dp,
                            )
                            .movable()
                            .resizable(),
                    ) {
                        PlantDetailScreen(
                            plant = plant,
                            onBackClick = {
                                selectedPlants.remove(plant)
                            },
                            onAddModelClick = {
                                modelInstances.add(
                                    Plant3dModelInstance(
                                        instanceId = instanceIdCounter.incrementAndGet(),
                                        plant = plant,
                                    ),
                                )
                            },
                        )
                    }
                }
            }
        } else {
            // Invisible anchor panel: keeps the orbiter floating in the same spot
            // while all real panels are hidden in immersive mode.
            SpatialPanel(
                modifier = SubspaceModifier
                    .width(700.dp)
                    .height(1000.dp),
            ) {
                ModelsToolbarOrbiter(
                    isImmersiveMode = isImmersiveMode,
                    onClearAllClick = {
                        modelInstances.clear()
                    },
                    onToggleImmersiveModeClick = { isImmersiveMode = false },
                    onToggleEnvironmentClick = toggleEnvironment,
                )
            }
        }
        modelInstances.forEachIndexed { index, instance ->
            key("model-${instance.instanceId}") {
                Plant3dModel(instance = instance, index = index)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpatialPlantsAppPreview() {
    PlantsTheme {
        SpatialPlantsApp()
    }
}