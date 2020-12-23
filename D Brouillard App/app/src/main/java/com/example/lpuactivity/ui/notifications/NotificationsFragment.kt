package com.example.lpuactivity.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.sevice.Builder
import com.example.lpuactivity.Retrofit_requests.api.sevice.Dservice
import kotlinx.android.synthetic.main.fragment_notifications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Looper
import com.example.lpuactivity.util.email
import com.example.lpuactivity.models.Userinfo
import android.os.Handler
import android.widget.Toast
import com.example.lpuactivity.models.Video1
import com.example.lpuactivity.util.access
import org.jetbrains.anko.doAsync
import java.util.*

var user_name=""

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getuser() // GET DATA FOR USER
        getPosCount()
        getAccCount()

    }







    fun getPosCount() {

        doAsync {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                val Dservice = Builder.buildService(Dservice::class.java)  // builder service from retrofit request
                val requestCall = Dservice.getpost(email!!, access!!) // email from loginfragment

                requestCall.enqueue(object : Callback<List<Video1>> {
                    override fun onResponse(
                        call: Call<List<Video1>>,
                        response: Response<List<Video1>>
                    ) {

                        val dservice = response.body()!!
                        println(response.body()!!)
                        if (response.isSuccessful) {

                            // SET VALUES FOR EACH TEXT FEILD
                            posted_task.text= response.body()!!.count().toString()+" Task Posted"

                        }
                    }

                    override fun onFailure(call: Call<List<Video1>>, t: Throwable) {
                        // Toast for failure
                        Toast.makeText(context,"Unable to load user",Toast.LENGTH_SHORT).show()

                    }

                })

            }
        }
    }





    fun getAccCount() {

        doAsync {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                val Dservice = Builder.buildService(Dservice::class.java)  // builder service from retrofit request
                val requestCall = Dservice.get_acc_post(email!!, access!!) // email from loginfragment

                requestCall.enqueue(object : Callback<List<Video1>> {
                    override fun onResponse(
                        call: Call<List<Video1>>,
                        response: Response<List<Video1>>
                    ) {

                        val dservice = response.body()!!
                        println(response.body()!!)
                        if (response.isSuccessful) {

                            // SET VALUES FOR EACH TEXT FEILD
                            accepted_task.text= response.body()!!.count().toString()+" Task Accepted"

                        }
                    }

                    override fun onFailure(call: Call<List<Video1>>, t: Throwable) {
                        // Toast for failure
                        Toast.makeText(context,"Unable to load user",Toast.LENGTH_SHORT).show()

                    }

                })

            }
        }
    }



    fun getuser() {

        doAsync {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                val Dservice = Builder.buildService(Dservice::class.java)  // builder service from retrofit request
                val requestCall = Dservice.getUser(email, access!!) // email from loginfragment

                requestCall.enqueue(object : Callback<Userinfo> {
                    override fun onResponse(
                        call: Call<Userinfo>,
                        response: Response<Userinfo>
                    ) {
                 
                        val dservice = response.body()!!
                        println(response.body()!!)
                        if (response.isSuccessful) {

                            // SET VALUES FOR EACH TEXT FEILD

                            profile_username.text =
                                dservice.Firstname.toLowerCase(Locale.getDefault()).capitalize(
                                    Locale.ROOT)
                            user_name=dservice.Firstname


                            profile_Number.text =
                                dservice.contactNo.toString().toLowerCase(Locale.getDefault())
                                    .capitalize(Locale.ROOT)


                            profile_Email.text = dservice.Email.toLowerCase(Locale.getDefault())
                                .capitalize(Locale.ROOT)


                        }
                    }

                    override fun onFailure(call: Call<Userinfo>, t: Throwable) {
                        // Toast for failure
                        Toast.makeText(context,"Unable to load user",Toast.LENGTH_SHORT).show()

                    }

                })

            }
        }
    }
}