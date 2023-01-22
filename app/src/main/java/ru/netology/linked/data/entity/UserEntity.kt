package ru.netology.linked.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.linked.domain.dto.User

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val login: String,
    val name: String,
    val avatar: String? = null,
){
    fun toDto(): User = User(
        id = id,
        login = login,
        name = name,
        avatar = avatar,
    )

    companion object {
        fun fromDto(user: User): UserEntity = with(user){
            return UserEntity(
                id = id,
                login = login,
                name = name,
                avatar = avatar,
            )
        }

    }
}

fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
fun List<User>.toEntity(): List<UserEntity> = map(UserEntity::fromDto)