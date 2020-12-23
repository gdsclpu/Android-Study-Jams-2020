package com.example.lpuactivity.Retrofit_requests.api

import com.example.lpuactivity.models.accessToken
import com.example.lpuactivity.models.defaultResponse
import retrofit2.Call
import retrofit2.http.*


interface Api {



  // update task after accepting
  @FormUrlEncoded
    @POST("tasks/{id}")
    fun updateTask(@Path("id") id: String,  @Field("Email") email: String?,@Header("Authorization") token: String ):Call<defaultResponse>


    @FormUrlEncoded
    // login user
    @POST("login")
    fun createUser(
        @Field("Email") email: String?,
        @Field("password") password: String?,

    ): Call<accessToken>



    @FormUrlEncoded
    @POST("tasks/{token}")
    fun postwork(@Field("Email")email: String?,
                 @Field("TaskTitle") TaskTitle: String?,
                 @Field("PostBy") PostBy: String?,
                 @Field("TaskDis") TaskDis: String?,
                 @Field("Price") Price: String?,
                 @Field("TaskPic") TaskPic: String?,
                 @Path("token") token: String
    ):Call<defaultResponse>


    @DELETE("tasks/{id}/{token}")
    fun deletepost(@Path("id")Id:String?, @Path("token") token: String):Call<defaultResponse>

    @PUT("tasks/acc/{id}/{token}")
    fun rejectTask(@Path("id")Id:String?, @Path("token") token: String):Call<defaultResponse>



    @FormUrlEncoded
    @POST("signup")
//    @Headers("Authorization:eyJhbGciOiJIUzI1NiJ9.YXl4eHVzaDI.pIp830uYSgWsuOOXRAFCgwjTJr12xw-UehSA8bX6358", "x-foo: bar")
    fun signupuser(@Field("Firstname")Firstname: String?,
                   @Field("Email") Email: String?,
                 @Field("contactNo") contactNo: Long?,
                 @Field("password") password: String?,


                 ):Call<defaultResponse>


}