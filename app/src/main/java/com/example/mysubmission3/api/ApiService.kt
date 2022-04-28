package com.example.mysubmission3.api

import com.example.mysubmission3.data.model.DetailUserResponse
import com.example.mysubmission3.data.model.User
import com.example.mysubmission3.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q")query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>> //menggunakan User karena objek data yang di ambil sama

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>


}