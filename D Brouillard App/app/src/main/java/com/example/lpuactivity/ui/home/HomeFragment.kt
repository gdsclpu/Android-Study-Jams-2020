package com.example.lpuactivity.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.sevice.Builder
import com.example.lpuactivity.Retrofit_requests.api.sevice.Dservice
import com.example.lpuactivity.models.Video
import com.example.lpuactivity.util.access
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fetchJson()  //fetch tasks








    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onResume()


        homefragmentrecycle.setOnClickListener {

            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()

        }
    }


    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }


    fun fetchJson(){

        // for fragment do run on ui thread

        doAsync {

            val handler = Handler(Looper.getMainLooper())
            handler.post {

                println(access)
                val Dservice = Builder.buildService(Dservice::class.java)
                val requestCall = Dservice.getTask(access!!)
                requestCall.enqueue(object : Callback<List<Video>> {
                    override fun onResponse(
                        call: Call<List<Video>>,
                        response: Response<List<Video>>
                    ) {

                        if (response.isSuccessful) {
                            val dservice = response.body()!!
                            println(dservice)
                            homefragmentrecycle.layoutManager =
                                GridLayoutManager(activity, 2) // homeadapter grid layout

                            homefragmentrecycle.adapter = Home_Adapter(dservice)
                        }
                    }

                    override fun onFailure(call: Call<List<Video>>, t: Throwable) {
                        Toast.makeText(context, "Unable to load tasks", Toast.LENGTH_SHORT).show()

                    }

                })
            }
        }

        }


    }





