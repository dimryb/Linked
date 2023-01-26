package ru.netology.linked.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    val formatterForServer = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
    val formatterForUser = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm")

    fun convertForUser(dateTimeServer: String): String {
        return try {
            val dateTime = LocalDateTime.parse(dateTimeServer, formatterForServer)
            dateTime.format(formatterForUser)
        } catch (e: java.lang.Exception) {
            dateTimeServer
        }
    }

}