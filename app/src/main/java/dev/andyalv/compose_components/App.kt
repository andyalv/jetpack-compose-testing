package dev.andyalv.compose_components

import android.app.Application
import dev.andyalv.compose_components.common.FoodRepository

class App: Application() {

    lateinit var repository: FoodRepository
        private set

    override fun onCreate() {
        super.onCreate()
        repository = FoodRepository(resources)
    }
}