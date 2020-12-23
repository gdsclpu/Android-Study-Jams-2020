package com.example.lpuactivity.Retrofit_requests.api.sevice

import com.example.lpuactivity.models.Userinfo
import com.example.lpuactivity.models.Video
import com.example.lpuactivity.models.Video1
import com.example.lpuactivity.models.accessToken
import com.example.lpuactivity.util.access

import retrofit2.Call
import retrofit2.http.*

interface Dservice {

    val a: String
        get() = access!!

    // get user info
    @GET("userinfo/{id}/{token}")
    fun getUser(@Path("id")id:String?, @Path("token") token: String): Call<Userinfo>

    // get all tasks

    @GET("tasks/{token}")
//    @Headers("Authorization"+ ${a} )
    fun getTask(@Path("token") token: String?): Call<List<Video>>


    @GET("tasks/{id}/{token}")
    fun getpost(@Path("id") id: String, @Path("token") token: String):Call<List<Video1>>


    @GET("tasks/acc/{id}/{token}")
    fun get_acc_post(@Path("id") id: String, @Path("token") token: String):Call<List<Video1>>

    @GET("tasks/yours/{id}/{token}")
    fun get_yours_post(@Path("id") id: String, @Path("token") token: String):Call<List<Video1>>

}