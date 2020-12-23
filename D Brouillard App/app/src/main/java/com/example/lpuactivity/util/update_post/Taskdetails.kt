package com.example.lpuactivity.util.update_post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.RetrofitClient
import com.example.lpuactivity.models.defaultResponse
import com.example.lpuactivity.util.FingerPrintManagementUtil
import com.example.lpuactivity.util.access
import com.example.lpuactivity.util.email
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executors



var id=""
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
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

        share_word.setOnClickListener{

            val message="Got Loads of Assignments? We got you covered with Débrouillard Post your task here and individuals around the globe will assist you with finishing it. " +
                    "download from www.techronx.com and see this amazing Task for You : " +title
            val intent = Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,message)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"share to: "))
        }



        detail_button.setOnClickListener {
            FingerPrintManagementUtil(this, Executors.newSingleThreadExecutor(), callback).showBiometricPrompt()

        }




    }



    private val callback = object : BiometricPrompt.AuthenticationCallback(){
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Log.e("MainActivity", errString.toString())
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            runOnUiThread {
                RetrofitClient.instance.updateTask(id, email, access!!).enqueue(object: Callback<defaultResponse>{
                    override fun onResponse(
                        call: Call<defaultResponse>,
                        response: Response<defaultResponse>
                    ) {
                        if(response.body()==null)
                        {
                            Toast.makeText(this@MainActivity2, "Can't accept Your own task", Toast.LENGTH_LONG).show()
                            finish()

                        }else{
                            println(response.body()!!)
                            Toast.makeText(this@MainActivity2, "Task Accepted", Toast.LENGTH_LONG).show()
                            finish()
                        }



                    }

                    override fun onFailure(call: Call<defaultResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity2, "Task failed to Accept", Toast.LENGTH_LONG).show()

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




