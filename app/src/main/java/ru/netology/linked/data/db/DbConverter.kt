package ru.netology.linked.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.beust.klaxon.Klaxon
import ru.netology.linked.domain.dto.UserPreview

@ProvidedTypeConverter
class DbConverter {

    @TypeConverter
    fun fromListLong(ids: List<Long>): String {
        return Klaxon().toJsonString(ids)
    }

    @TypeConverter
    fun toListLong(data: String): List<Long> {
        return Klaxon().parseArray(data) ?: emptyList()
    }

    @TypeConverter
    fun fromMapUsers(map: Map<Long, UserPreview>): String {
        return Klaxon().toJsonString(map)
    }

    @TypeConverter
    fun toMapUsers(data: String): Map<Long, UserPreview> {
        return Klaxon().parse<Map<Long, UserPreview>>(data) ?: emptyMap()
    }

}