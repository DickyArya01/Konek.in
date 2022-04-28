package com.example.mysubmission3.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmission3.api.ApiConfig
import com.example.mysubmission3.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String){
        ApiConfig.getApiService()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                   if (response.isSuccessful)
                       listFollowers.postValue(response.body())
                    else
                        Log.d("OnFailure", response.message().toString())
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Onfailure",t.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>>{
        return listFollowers
    }
}