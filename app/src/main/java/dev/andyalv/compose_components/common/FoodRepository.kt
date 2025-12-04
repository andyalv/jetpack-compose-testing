package dev.andyalv.compose_components.common

import android.content.res.Resources
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.andyalv.compose_components.R
import dev.andyalv.compose_components.food_item.FoodItemDetail
import dev.andyalv.compose_components.menu.FoodItemGeneral
import java.util.UUID

class FoodRepository(resources: Resources) {
    private var foodList: List<FoodItemGeneral> = emptyList()
    private var foodDetail: List<FoodItemDetail> = emptyList()

    init {
        foodList = loadListFromJson(resources, R.raw.food_list)
        foodDetail = loadListFromJson(resources, R.raw.food_details)
    }

    private inline fun <reified T> loadListFromJson(
        resources: Resources,
        @RawRes resId: Int,
        gson: Gson?= null
    ): List<T> {
        val gsonInstance = gson ?: GsonBuilder()
            .registerTypeAdapter(UUID::class.java, UUIDDeserializer())
            .create()

        val json = resources.openRawResource(resId)
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<T>>() {}.type
        return gsonInstance.fromJson(json, type)
    }

    fun getFoodList() = foodList
    fun getFoodDetail(id: UUID) = foodDetail.first { it.id == id }
    fun getFoodItem(id: UUID) = foodList.first { it.id == id }
}