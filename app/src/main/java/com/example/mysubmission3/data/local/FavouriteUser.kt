package com.example.mysubmission3.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourite_user")
data class FavouriteUser(
    val login: String,
    val avatar: String,
    @PrimaryKey
    val id: Int
):Serializable
