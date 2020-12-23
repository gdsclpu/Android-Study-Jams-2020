package com.example.lpuactivity.util.Accepted_post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.sevice.Builder
import com.example.lpuactivity.Retrofit_requests.api.sevice.Dservice
import com.example.lpuactivity.models.Video1
import com.example.lpuactivity.util.Personal_Post.personal_post_adaptar
import com.example.lpuactivity.util.access
import com.example.lpuactivity.util.email
import kotlinx.android.synthetic.main.activity_show_accepted_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Show_accepted_post : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_accepted_post)
        supportActionBar?.hide()

        get_acc_post()


    }



   fun get_acc_post(){
       val Dservice = Builder.buildService(Dservice::class.java)
       val requestCall = Dservice.get_acc_post(email!!, access!!)
       requestCall.enqueue(object : Callback<List<Video1>> {
           override fun onResponse(
               call: Call<List<Video1>>,
               response: Response<List<Video1>>
           ) {

               if (response.isSuccessful) {
                   val dservice = response.body()!!

                   if(response.body()!!.count()==0){
                       accepted_task_no_task.text="You accepted no tasks"

                   }

                   accepted_post.layoutManager =
                       GridLayoutManager(this@Show_accepted_post, 2) // homeadapter grid layout

                   accepted_post.adapter = accepted_post_adaptar(dservice)
               }
           }

           override fun onFailure(call: Call<List<Video1>>, t: Throwable) {
               Toast.makeText(this@Show_accepted_post, "Unable to load tasks", Toast.LENGTH_SHORT).show()

           }

       })
   }








}