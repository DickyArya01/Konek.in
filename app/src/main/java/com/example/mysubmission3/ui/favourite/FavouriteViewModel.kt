package com.example.mysubmission3.ui.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mysubmission3.data.local.FavouriteUser
import com.example.mysubmission3.data.local.FavouriteUserDao
import com.example.mysubmission3.data.local.UserDatabase

class FavouriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: FavouriteUserDao? =  userDb?.favouriteUserDao()

    fun getFavouriteUser(): LiveData<List<FavouriteUser>>{
        return userDao!!.getFavouriteUser()
    }

}