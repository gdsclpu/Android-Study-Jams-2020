package com.example.lpuactivity.util.Personal_Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.sevice.Builder
import com.example.lpuactivity.Retrofit_requests.api.sevice.Dservice
import com.example.lpuactivity.models.Video1
import com.example.lpuactivity.util.access
import com.example.lpuactivity.util.email
import kotlinx.android.synthetic.main.activity_see_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeePost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_post)
        supportActionBar?.hide()
        getPost()

    }

    fun getPost(){

        val post =  Builder.buildService(Dservice::class.java)
        println(email)
        val requestCall = post.getpost(email!!, access!!)
        requestCall.enqueue(object : Callback<List<Video1>> {
            override fun onResponse(
                call: Call<List<Video1>>,
                response: Response<List<Video1>>
            ) {

                if (response.isSuccessful) {
                    val sp1 = response.body()!!
                    println(sp1)
                    personal_post.layoutManager =
                        GridLayoutManager(this@SeePost, 2)

                    personal_post.adapter = personal_post_adaptar(sp1)
                }
            }

            override fun onFailure(call: Call<List<Video1>>, t: Throwable) {
                Toast.makeText(this@SeePost, "Unable to load Post", Toast.LENGTH_SHORT).show()

            }

        })



    }
}