package com.example.lpuactivity.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lpuactivity.R
import com.example.lpuactivity.util.postTask.Posttask
import com.example.lpuactivity.util.Personal_Post.SeePost
import com.example.lpuactivity.util.Accepted_post.Show_accepted_post
import com.example.lpuactivity.util.your_accepted_post.your_accepted_work
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {

        })



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.commit();

//        activity?.onBackPressed();

        dashbord_posttask!!.setOnClickListener {

            val intent = Intent(context, Posttask::class.java)
            startActivity(intent)

        }

        dashbord_seepost!!.setOnClickListener {
            val intent = Intent(context, SeePost::class.java)
            startActivity(intent)
        }

        your_accepted_task!!.setOnClickListener {
            val intent = Intent(context, Show_accepted_post::class.java)
            startActivity(intent)

        }
        your_accepted_task_dashboard!!.setOnClickListener {
            val intent = Intent(context,your_accepted_work::class.java)
            startActivity(intent)

        }




    }











}