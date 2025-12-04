package dev.andyalv.compose_components.common

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.UUID

class UUIDDeserializer : JsonDeserializer<UUID> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): UUID {
        return UUID.fromString(json.asString)
    }
}