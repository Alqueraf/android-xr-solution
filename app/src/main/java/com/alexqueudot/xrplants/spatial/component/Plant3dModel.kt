package com.alexqueudot.xrplants.spatial.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.xr.compose.platform.LocalSession
import androidx.xr.runtime.math.Pose
import androidx.xr.runtime.math.Vector3
import androidx.xr.scenecore.GltfModel
import androidx.xr.scenecore.GltfModelEntity
import androidx.xr.scenecore.MovableComponent
import androidx.xr.scenecore.scene
import com.alexqueudot.xrplants.data.Plant
import java.nio.file.Path

private const val MODEL_POSITION_X_METERS = -0.6f
private const val MODEL_POSITION_Y_METERS = -0.2f
private const val MODEL_POSITION_Z_METERS = 0.4f
private const val MODEL_SPACING_METERS = 0.75f
private const val TAG = "Plant3dModel"

data class Plant3dModelInstance(
    val instanceId: Long,
    val plant: Plant,
)

@Composable
internal fun Plant3dModel(instance: Plant3dModelInstance, index: Int = 0) {
    val session = LocalSession.current ?: return
    var entity by remember(instance.instanceId) { mutableStateOf<GltfModelEntity?>(null) }

    LaunchedEffect(instance.instanceId) {
        try {
            val model = GltfModel.create(session, Path.of(instance.plant.modelAsset))
            val modelEntity = GltfModelEntity.create(
                session = session,
                model = model,
                pose = Pose(
                    translation = Vector3(
                        x = MODEL_POSITION_X_METERS - index * MODEL_SPACING_METERS,
                        y = MODEL_POSITION_Y_METERS,
                        z = MODEL_POSITION_Z_METERS,
                    ),
                ),
                parent = session.scene.activitySpace,
            )
            modelEntity.setScale(instance.plant.modelScale)
            modelEntity.addComponent(MovableComponent.createSystemMovable(session = session))
            entity = modelEntity
        } catch (e: Exception) {
            Log.w(TAG, "failed to load 3D model ${instance.plant.modelAsset}", e)
        }
    }

    DisposableEffect(instance.instanceId) {
        onDispose {
            entity?.dispose()
            entity = null
        }
    }
}