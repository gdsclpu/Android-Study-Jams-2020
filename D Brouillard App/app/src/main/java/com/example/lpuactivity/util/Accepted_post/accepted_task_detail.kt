package com.example.lpuactivity.util.Accepted_post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.RetrofitClient
import com.example.lpuactivity.Retrofit_requests.api.sevice.Builder
import com.example.lpuactivity.Retrofit_requests.api.sevice.Dservice
import com.example.lpuactivity.models.Userinfo
import com.example.lpuactivity.models.defaultResponse
import com.example.lpuactivity.ui.notifications.user_name
import com.example.lpuactivity.util.FingerPrintManagementUtil
import com.example.lpuactivity.util.access
import com.example.lpuactivity.util.email
import com.example.lpuactivity.util.update_post.id
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_accepted_task_detail.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main2.detail_button
import kotlinx.android.synthetic.main.activity_main2.detail_description
import kotlinx.android.synthetic.main.activity_main2.detail_name
import kotlinx.android.synthetic.main.activity_main2.detail_price
import kotlinx.android.synthetic.main.activity_main2.detailimage
import kotlinx.android.synthetic.main.activity_main2.mainactivity2textview
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executors

class accepted_task_detail : AppCompatActivity() {

        var UserName=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accepted_task_detail)
        supportActionBar?.hide()


        println(intent.getStringExtra("Price"))

        var intent = intent
        var title=intent.getStringExtra("Title")

        val image=intent.getStringExtra("Image")
        val postby=intent.getStringExtra("Postby")
        val price: String? ="₹ "+intent.getStringExtra("Price")
        val description=intent.getStringExtra("Description")
        id = intent.getStringExtra("Id").toString()


        if (postby != null) {
            detail_name.text=postby.toLowerCase(Locale.getDefault())?.capitalize(Locale.ROOT)
        }
        if (title != null) {
            mainactivity2textview.text=title.toLowerCase(Locale.getDefault())?.capitalize(Locale.ROOT)
        }
        detail_price.text=price
        detail_description.text=description

        Picasso.get()
            .load(image)
            .fit()
            .centerCrop()
            .into(detailimage)


        getuser()
        detail_button.setOnClickListener {
            FingerPrintManagementUtil(this, Executors.newSingleThreadExecutor(), callback).showBiometricPrompt()

        }




    }



    fun getuser() {


        val Dservice = Builder.buildService(Dservice::class.java)  // builder service from retrofit request
        val requestCall = Dservice.getUser(email!!, access!!) // email from loginfragment

        requestCall.enqueue(object : Callback<Userinfo> {
            override fun onResponse(
                call: Call<Userinfo>,
                response: Response<Userinfo>
            ) {

                val dservice = response.body()!!
                println(response.body()!!)
                if (response.isSuccessful) {

                    // SET VALUES FOR EACH TEXT FEILD



                    UserName =dservice.Firstname
                    detail_number.text=dservice.contactNo.toString()



                }
            }

            override fun onFailure(call: Call<Userinfo>, t: Throwable) {
                // Toast for failure
                Toast.makeText(this@accepted_task_detail,"Unable to load user",Toast.LENGTH_SHORT).show()

            }



        })

    }




    private val callback = object : BiometricPrompt.AuthenticationCallback(){
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Log.e("MainActivity", errString.toString())
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            runOnUiThread {
                Toast.makeText(this@accepted_task_detail, id, Toast.LENGTH_LONG).show()
                RetrofitClient.instance.rejectTask(id, access!!).enqueue(object:
                    Callback<defaultResponse> {
                    override fun onResponse(
                        call: Call<defaultResponse>,
                        response: Response<defaultResponse>
                    ) {

                        Toast.makeText(this@accepted_task_detail, "Task Rejected", Toast.LENGTH_LONG).show()
                        finish()


                    }

                    override fun onFailure(call: Call<defaultResponse>, t: Throwable) {
                        Toast.makeText(this@accepted_task_detail, "Task failed to Reject", Toast.LENGTH_LONG).show()

                    }

                })

            }
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Log.e("MainActivity", " Failed login")
        }
    }
}




