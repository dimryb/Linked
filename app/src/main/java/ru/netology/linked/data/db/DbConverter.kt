package ru.netology.linked.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class DbConverter {

    @TypeConverter
    fun fromListLong(ids: List<Long>): String {
        return ids.joinToString(separator = ",")
    }

    @TypeConverter
    fun toListLong(data: String): List<Long> {
        return try {
            data.split(",").map { it.toLong() }
        } catch (e: RuntimeException) {
            emptyList()
        }
    }
}