package com.alexqueudot.xrplants.twod

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alexqueudot.xrplants.data.Plants
import com.alexqueudot.xrplants.ui.screen.PlantDetailScreen
import com.alexqueudot.xrplants.ui.screen.PlantListScreen
import com.alexqueudot.xrplants.ui.theme.PlantsTheme

private const val ARG_PLANT_ID = "plantId"
private const val PLANT_LIST = "plants"
private const val PLANT_DETAIL = "plant/{$ARG_PLANT_ID}"

private fun plantDetail(plantId: String): String {
    return "plant/$plantId"
}

@Composable
fun PlantsNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PLANT_LIST,
    ) {
        composable(PLANT_LIST) {
            PlantListScreen(
                onPlantClick = { plant ->
                    navController.navigate(plantDetail(plant.id))
                },
            )
        }
        composable(
            route = PLANT_DETAIL,
            arguments = listOf(
                navArgument(ARG_PLANT_ID) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString(ARG_PLANT_ID)
            val plant = Plants.all.firstOrNull { plant -> plant.id == plantId }
            if (plant != null) {
                PlantDetailScreen(
                    plant = plant,
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun PlantsNavHostPreview() {
    PlantsTheme {
        PlantsNavHost()
    }
}
