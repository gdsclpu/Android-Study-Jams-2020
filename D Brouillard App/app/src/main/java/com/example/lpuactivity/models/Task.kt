package com.example.lpuactivity.models

import com.google.gson.annotations.SerializedName


data class Video(
    @SerializedName("TaskTitle")
    val title:String,
    @SerializedName("TaskPic")
    val photos:String,
    @SerializedName("TaskDis")
    val description:String,
    @SerializedName("Price")
    val price:String,
    @SerializedName("PostBy")
    val postby:String,
    @SerializedName("Upvote")
    val upvote:Int,
    @SerializedName("Email")
    val email:String,
    @SerializedName("IsDone")
    val isdone:Boolean,
    @SerializedName("_id")
    val id:String




)


data class Video1(
    @SerializedName("TaskTitle")
    val title:String,
    @SerializedName("TaskPic")
    val photos:String,
    @SerializedName("TaskDis")
    val description:String,
    @SerializedName("Price")
    val price:String,
    @SerializedName("PostBy")
    val postby:String,
    @SerializedName("Upvote")
    val upvote:Int,
    @SerializedName("Email")
    val email:String,
    @SerializedName("Doneby")
    val doneby:String,
    @SerializedName("IsDone")
    val isdone:Boolean,
    @SerializedName("_id")
    val id:String




)
// TASK COMING FROM /tasks