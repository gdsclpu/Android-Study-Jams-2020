package com.example.lpuactivity.models

import com.google.gson.annotations.SerializedName


//data class Userinfo (val Email:String,val TaskTitle:String,val PostBy:String,val TaskDis:String,val TaskPic:String,val Upvote:String,val Price:String,val IsDone:Boolean,val Doneby:Boolean)
data class Userinfo (
    @SerializedName("Firstname")
    val Firstname:String,
    @SerializedName("Email")
    val Email:String,
    @SerializedName("contactNo")
    val contactNo:Long,
    @SerializedName("password")
    val password:String,
    @SerializedName("_id")
    val _id:String


)

// get request from /userinfo/{id}
