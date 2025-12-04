package dev.andyalv.compose_components.food_item

import java.util.UUID

data class FoodItemDetail(
    val id: UUID,
    val optionList: List<OptionIndividual>,
    val extraList: List<ExtraIndividual>
)

data class OptionIndividual(
    val label: String,
    val choices: List<String>
)

data class ExtraIndividual(
    val label: String,
    val price: Double
)