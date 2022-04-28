package com.example.mysubmission3.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mysubmission3.api.ApiConfig
import com.example.mysubmission3.data.local.FavouriteUser
import com.example.mysubmission3.data.local.FavouriteUserDao
import com.example.mysubmission3.data.local.UserDatabase
import com.example.mysubmission3.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val user = MutableLiveData<DetailUserResponse>()

    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: FavouriteUserDao? =  userDb?.favouriteUserDao()


    fun setUserDetail(username: String) {
        ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    } else {
                        Log.d("OnFailure", response.message().toString())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("OnFailure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun getFavouriteUser(): LiveData<List<FavouriteUser>> = userDao!!.getFavouriteUser()

    fun addFavourite(username: String, avatar: String,id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavouriteUser(username,avatar, id)
            userDao?.addFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFavourite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteUser(id)
        }
    }
}