package com.example.lpuactivity.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.RetrofitClient
import com.example.lpuactivity.models.accessToken
import com.example.lpuactivity.models.defaultResponse
import com.example.lpuactivity.ui.notifications.NotificationsFragment
import com.example.lpuactivity.util.Accepted_post.accepted_task_detail
import kotlinx.android.synthetic.main.activity_login_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var email:String?=""
var access:String?=""
class LoginScreen : AppCompatActivity() {
    private val final:String="example.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        LoadData() //load data

        signup_id.setOnClickListener {
            val intent = Intent (this, signup::class.java)
            startActivity(intent)
        }

        signup_button.setOnClickListener {
             email =username.text.toString().trim()
            val Password=password.text.toString().trim()


            if(email!!.isEmpty())
            {
                username.error="Email required"
                username.requestFocus()
                return@setOnClickListener
            }
            if(Password.isEmpty())
            {
               password.error="Email required"
                password.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(
                email,
                Password,
            ).enqueue(object : Callback<accessToken> {
                override fun onResponse(
                    call: Call<accessToken>,
                    response: Response<accessToken>
                ) {

                    if (response.body() != null) {


                        access=response.body()!!.accessT
                        saveData()
                        val intent = Intent(this@LoginScreen, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        println(response.body()!!.accessT)

                        Toast.makeText(this@LoginScreen, "Fail to login", Toast.LENGTH_SHORT).show()

                    }


                }

                override fun onFailure(call: Call<accessToken>, t: Throwable) {


                    Toast.makeText(this@LoginScreen, "Fail", Toast.LENGTH_SHORT).show()
                }

            }

            )

        }

    }








        fun saveData()
        {
            val text:String="true"

            val sharedPreferences:SharedPreferences=getSharedPreferences(
                "sharedPrefs",
                Context.MODE_PRIVATE
            )
            val editor=sharedPreferences.edit()
            editor.apply{
                putString("email", email)
                putBoolean("BOOLEAN_KEY", true)
                putString("accessToken", access)

            }.apply()
            Toast.makeText(this@LoginScreen, "saved ", Toast.LENGTH_LONG).show()



        }




    fun LoadData()

    {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)!!

        val preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.clear()
//        editor.apply()

        access = sharedPreferences.getString("accessToken", "")

        email = sharedPreferences.getString("email", "fail")?.toString()
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY", false)


        println(savedBoolean)
        if(savedBoolean){

            val intent = Intent(this@LoginScreen, MainActivity::class.java)
            startActivity(intent)
        }else{
            println(savedBoolean)
        }

    }







}