package com.example.lpuactivity.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.RetrofitClient
import com.example.lpuactivity.models.defaultResponse
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        supportActionBar?.hide()
//        LoadData() //load data

        signup_main_button.setOnClickListener {
            email = signup_email.text.toString().trim()
            val Password = signup_password.text.toString().trim()
            val name=signup_name.text.toString().trim()
            val contactNo=signup_number.text.toString().toLong()


            if (email!!.isEmpty()) {
                username.error = "Email required"
                username.requestFocus()
                return@setOnClickListener
            }
            if (Password.isEmpty()) {
                password.error = "Password required"
                password.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.signupuser(
                name,
                email,
                contactNo,
                Password,
            ).enqueue(object : Callback<defaultResponse> {
                override fun onResponse(
                    call: Call<defaultResponse>,
                    response: Response<defaultResponse>
                ) {

                    println(response.body())
                    if (response.body() != null) {
                        saveData()
//                        val fragment = NotificationsFragment()
//                        fragment.getuser()
                        val intent = Intent(this@signup, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@signup, "Fail to sign up", Toast.LENGTH_SHORT).show()

                    }


                }

                override fun onFailure(call: Call<defaultResponse>, t: Throwable) {
                    Toast.makeText(this@signup, "to sign up", Toast.LENGTH_SHORT).show()
                }

            }

            )

        }

    }


    fun saveData() {
        val text: String = "true"

        val sharedPreferences: SharedPreferences = getSharedPreferences(
            "sharedPrefs",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("email", email)
            putBoolean("BOOLEAN_KEY", true)

        }.apply()
        Toast.makeText(this@signup, "saved ", Toast.LENGTH_LONG).show()


    }


    fun LoadData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)!!

//        val preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.clear()
//        editor.apply()
//        finish()
        val savedString = sharedPreferences.getString("STRING_KEY", null)
        email = sharedPreferences.getString("email", "fail")?.toString()
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY", false)


        println(savedBoolean)
        if (savedBoolean) {

            val intent = Intent(this@signup, MainActivity::class.java)
            startActivity(intent)
        } else {
            println(savedBoolean)
        }

    }
}







