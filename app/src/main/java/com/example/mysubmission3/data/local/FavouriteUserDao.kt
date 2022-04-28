package com.example.mysubmission3.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteUserDao {
    @Insert
    suspend fun addFavorite(favouriteUser: FavouriteUser)

    @Query("SELECT * FROM favourite_user")
    fun getFavouriteUser(): LiveData<List<FavouriteUser>>

    @Query("SELECT count(*) FROM favourite_user WHERE favourite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favourite_user WHERE favourite_user.id = :id")
    suspend fun deleteUser(id: Int): Int


}