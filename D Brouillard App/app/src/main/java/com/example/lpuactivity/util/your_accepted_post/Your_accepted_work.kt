package com.example.lpuactivity.util.your_accepted_post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.sevice.Builder
import com.example.lpuactivity.Retrofit_requests.api.sevice.Dservice
import com.example.lpuactivity.models.Video1
import com.example.lpuactivity.util.FingerPrintManagementUtil
import com.example.lpuactivity.util.Personal_Post.Per_id
import com.example.lpuactivity.util.Personal_Post.personal_post_adaptar
import com.example.lpuactivity.util.access
import com.example.lpuactivity.util.email
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_see_post.*
import kotlinx.android.synthetic.main.activity_your_accepted_work.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executors

class your_accepted_work : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_accepted_work)


        supportActionBar?.hide()


        getPost()




    }


    fun getPost(){

        val post =  Builder.buildService(Dservice::class.java)
        println(email)
        val requestCall = post.get_yours_post(email!!, access!!)
        requestCall.enqueue(object : Callback<List<Video1>> {
            override fun onResponse(
                call: Call<List<Video1>>,
                response: Response<List<Video1>>
            ) {

                if (response.isSuccessful) {
                    val sp1 = response.body()!!

                    if(response.body()!!.count()==0){
                        your_accepted_task_no_task.text="You have no Task Accepted"
                    }
                    your_accepted_task_detail.layoutManager =
                        LinearLayoutManager(this@your_accepted_work)

                    your_accepted_task_detail.adapter = your_accepted_task_adapter(sp1)
                }
            }

            override fun onFailure(call: Call<List<Video1>>, t: Throwable) {
                Toast.makeText(this@your_accepted_work, "Unable to load Post", Toast.LENGTH_SHORT).show()

            }

        })



    }



}