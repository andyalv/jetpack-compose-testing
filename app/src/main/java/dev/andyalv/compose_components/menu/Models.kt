package dev.andyalv.compose_components.menu

import java.util.UUID

data class FoodItemGeneral(
    val id: UUID,
    val title: String,
    val description: String,
    val price: Double,
    val priceDiscount: Double? = null,
    val likeability: FoodLikeability? = null,
    val image: FoodItemImageData
)

data class FoodItemImageData(
    val url: String,
    val description: String? = null,
)

data class FoodLikeability(
    val likePercentage: Int,
    val likeUsers: Int
) {
    init {
        require(likePercentage > 0) { "likePercentage must be higher than zero" }
        require(likeUsers > 0) { "likeUsers must be higher than zero" }
    }
}