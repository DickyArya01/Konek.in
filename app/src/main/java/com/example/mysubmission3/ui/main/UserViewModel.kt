package com.example.mysubmission3.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.mysubmission3.api.ApiConfig
import com.example.mysubmission3.data.model.User
import com.example.mysubmission3.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

   fun setSearchUser(query: String){
       ApiConfig.getApiService()
           .getSearchUsers(query)
           .enqueue(object : Callback<UserResponse>{
               override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                  if (response.isSuccessful){
                      listUsers.postValue(response.body()?.items)
                  }
               }

               override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                   Log.d("Failure",t.message.toString())
               }

           })
   }

    fun getSearchUser(): LiveData<ArrayList<User>>{
        return listUsers
    }
}