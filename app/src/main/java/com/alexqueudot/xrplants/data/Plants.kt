package com.alexqueudot.xrplants.data

import androidx.annotation.DrawableRes
import com.alexqueudot.xrplants.R

enum class WaterNeeds(val label: String, val level: Int) {
    LOW("Low water", 1),
    MEDIUM("Medium water", 2),
    HIGH("High water", 3),
}

enum class SunNeeds(val label: String, val level: Int) {
    FULL_SUN("Full sun", 3),
    PARTIAL_SUN("Partial sun", 2),
    SHADE("Shade", 1),
}

enum class PlantCategory(val label: String) {
    TREE("Tree"),
    SHRUB("Shrub"),
    HERB("Herb"),
    FLOWER("Flower"),
}

data class Plant(
    val id: String,
    val name: String,
    val description: String,
    @param:DrawableRes val imageRes: Int,
    val modelAsset: String,
    val modelScale: Float,
    val category: PlantCategory,
    val waterNeeds: WaterNeeds,
    val sunNeeds: SunNeeds,
)

object Plants {
    val all = listOf(
        Plant(
            id = "OLIVE_TREE",
            name = "Olive Tree",
            description = "A slow-growing evergreen tree with silvery-green leaves, prized for its fruit and oil. Native to the Mediterranean, it can live for centuries.",
            imageRes = R.drawable.plant_olive_tree,
            modelAsset = "models/olive_tree.glb",
            modelScale = 0.06f,
            category = PlantCategory.TREE,
            waterNeeds = WaterNeeds.LOW,
            sunNeeds = SunNeeds.FULL_SUN,
        ),
        Plant(
            id = "AFRICAN_DAISY",
            name = "African Daisy",
            description = "A cheerful daisy-like flower from South Africa with vivid petals and a striking blue-purple center. Thrives in full sun and blooms from spring to autumn.",
            imageRes = R.drawable.plant_african_daisy,
            modelAsset = "models/african_daisy.glb",
            modelScale = 0.33f,
            category = PlantCategory.FLOWER,
            waterNeeds = WaterNeeds.MEDIUM,
            sunNeeds = SunNeeds.FULL_SUN,
        ),
        Plant(
            id = "LAVENDER",
            name = "Lavender",
            description = "A fragrant flowering shrub with purple spikes and aromatic foliage. Loves full sun and well-drained soil, and attracts bees and butterflies.",
            imageRes = R.drawable.plant_lavender,
            modelAsset = "models/lavender.glb",
            modelScale = 0.18f,
            category = PlantCategory.SHRUB,
            waterNeeds = WaterNeeds.LOW,
            sunNeeds = SunNeeds.FULL_SUN,
        ),
        Plant(
            id = "THYME",
            name = "Thyme",
            description = "A low-growing woody herb with tiny aromatic leaves and delicate purple flowers. Drought-tolerant, loves full sun, and a staple of Mediterranean cooking.",
            imageRes = R.drawable.plant_thyme,
            modelAsset = "models/thyme.glb",
            modelScale = 0.67f,
            category = PlantCategory.HERB,
            waterNeeds = WaterNeeds.LOW,
            sunNeeds = SunNeeds.FULL_SUN,
        ),
    )
}
