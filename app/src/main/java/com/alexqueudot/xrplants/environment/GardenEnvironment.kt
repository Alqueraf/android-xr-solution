package com.alexqueudot.xrplants.environment

import androidx.xr.runtime.Session
import androidx.xr.scenecore.GltfModel
import androidx.xr.scenecore.ImageBasedLightingAsset
import androidx.xr.scenecore.SpatialEnvironment
import java.nio.file.Path
import kotlin.io.path.Path

enum class GardenEnvironment(
    val iblPath: String?,
    val geometryPath: String?,
) {
    DEFAULT(
        iblPath = null,
        geometryPath = null,
    ),
    GREEN_HILLS(
        iblPath = "green_hills_ibl.zip",
        geometryPath = "green_hills_ktx2_mipmap.glb",
    ),
    ;

    fun next(): GardenEnvironment {
        return entries[(ordinal + 1) % entries.size]
    }

    suspend fun toSpatialEnvironmentPreference(
        session: Session,
    ): SpatialEnvironment.SpatialEnvironmentPreference? {
        val ibl = iblPath?.let { iblPath ->
            ImageBasedLightingAsset.createFromZip(session, Path(iblPath))
        }
        val geometry = geometryPath?.let { geometryPath ->
            GltfModel.create(session, Path(geometryPath))
        }
        return if (ibl == null && geometry == null) {
            null
        } else {
            SpatialEnvironment.SpatialEnvironmentPreference(ibl, geometry)
        }
    }
}