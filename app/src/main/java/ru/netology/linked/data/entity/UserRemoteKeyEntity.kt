package ru.netology.linked.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserRemoteKeyEntity(
    @PrimaryKey
    val type: KeyType,
    val key: Long,
){
    enum class KeyType {
        AFTER,
        BEFORE,
    }
}
