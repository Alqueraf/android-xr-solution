package com.alexqueudot.xrplants

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.xr.compose.platform.LocalSpatialCapabilities
import com.alexqueudot.xrplants.spatial.SpatialPlantsApp
import com.alexqueudot.xrplants.twod.PlantsNavHost
import com.alexqueudot.xrplants.ui.theme.PlantsTheme

@Composable
fun PlantsApp() {
    val isSpatialUiEnabled = LocalSpatialCapabilities.current.isSpatialUiEnabled

    if (!isSpatialUiEnabled) {
        // 2D path: phones, tablets and XR Home Space.
        PlantsNavHost()
    } else {
        // Spatial path: XR Full Space only. The Subspace must not be composed outside Full
        // Space, as it disables the main panel as soon as it enters the composition.
        SpatialPlantsApp()
    }
}

@Preview
@Composable
fun PlantsAppPreview() {
    PlantsTheme {
        PlantsApp()
    }
}