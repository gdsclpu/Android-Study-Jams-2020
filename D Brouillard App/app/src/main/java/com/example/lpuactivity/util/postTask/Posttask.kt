package com.example.lpuactivity.util.postTask
import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lpuactivity.R
import com.example.lpuactivity.Retrofit_requests.api.RetrofitClient
import com.example.lpuactivity.Retrofit_requests.api.sevice.Builder
import com.example.lpuactivity.Retrofit_requests.api.sevice.Dservice
import com.example.lpuactivity.models.Userinfo
import com.example.lpuactivity.models.defaultResponse
import com.example.lpuactivity.util.access
import com.example.lpuactivity.util.email
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_posttask.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Posttask : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 1001
    private val PICK_IMAGE_REQUEST = 900;
    var userName = "";
    lateinit var filePath : Uri
   var imagename="https://firebasestorage.googleapis.com/v0/b/virtusa-58806.appspot.com/o/images%2Fdefault-post-pic.png?alt=media&token=1459f509-aaa4-4f0e-a4f6-57331eaad74a"

    @TargetApi(Build.VERSION_CODES.M)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_posttask)

        image_post.setOnClickListener {
            when {
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) -> {
                    if (ContextCompat.checkSelfPermission(this@Posttask, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
                    }else{
                        chooseFile()
                    }
                }

                else -> chooseFile()
            }

        }
        getuser()
        post_button.setOnClickListener {

            Upload_Post()
        }

        supportActionBar?.title= "Publier"




    }






    private fun chooseFile() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }







    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            PERMISSION_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this@Posttask, "Oops! Permission Denied!!",Toast.LENGTH_SHORT).show()
                else
                    chooseFile()
            }
        }

    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        when (requestCode){
            PICK_IMAGE_REQUEST -> {
                filePath = data!!.data!!

                image_post.setImageURI(filePath)


                uploadFile()

            }
        }
    }





    private fun uploadFile() {
        val progress = ProgressDialog(this).apply {
            setTitle("Uploading Picture....")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }

        val data = FirebaseStorage.getInstance()
        println(filePath)
        var namefile: String =filePath.toString()
        val ref = data.reference.child("images/"+namefile.substring(namefile.lastIndexOf("/") + 1))
        post_text.text=namefile
        var uploadTask = ref.putFile(filePath)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }


            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var downloadUri = task.result!!
                println(downloadUri)
                imagename=downloadUri.toString()
                progress.setMessage("Uploaded.. ")
                progress.dismiss()
            } else {
                Toast.makeText(this@Posttask, "Uploading failed",Toast.LENGTH_SHORT).show()

            }
        }
            .addOnFailureListener{
                    exception -> exception.printStackTrace()
            }


    }


    fun getuser() {

                val Dservice = Builder.buildService(Dservice::class.java)  // builder service from retrofit request
                val requestCall = Dservice.getUser(email,access!!) // email from loginfragment

                requestCall.enqueue(object : Callback<Userinfo> {
                    override fun onResponse(
                        call: Call<Userinfo>,
                        response: Response<Userinfo>
                    ) {


                        println(response.body()!!)
                        if (response.isSuccessful) {

                            // SET VALUES FOR EACH TEXT FEILD


                            userName = response.body()!!.Firstname







                        }
                    }

                    override fun onFailure(call: Call<Userinfo>, t: Throwable) {
                        // Toast for failure
                        Toast.makeText(this@Posttask,"Unable to load user",Toast.LENGTH_SHORT).show()
                        finish()

                    }

                })


    }







    private fun Upload_Post(){


        val post_title=post_title_text.text.toString().trim()
        val post_des=post_Des_text.text.toString().trim()
        val post_price=post_price_text.text.toString().trim()

        post_button.setOnClickListener {


        if(post_title.isEmpty()){
            post_title_text.error="Title required"
            post_title_text.requestFocus()
            return@setOnClickListener
        }

            if(post_des.isEmpty()){
                post_Des_text.error="Description required"
                post_Des_text.requestFocus()
                return@setOnClickListener
            }

            if(post_price.isEmpty())

            {
                post_price_text.error="Price required"
                post_price_text.requestFocus()
                return@setOnClickListener
            }
        }






        RetrofitClient.instance.postwork(email,post_title, userName,post_des,post_price,imagename,
         access!!).enqueue(object :
            Callback<defaultResponse> {
            override fun onResponse(
                call: Call<defaultResponse>,
                response: Response<defaultResponse>
            ) {

                println(response.body())
                if (response.body() != null) {
                    Toast.makeText(this@Posttask, "Done", Toast.LENGTH_SHORT).show()
                    finish()


                } else {
                    Toast.makeText(this@Posttask, "Unable to process request", Toast.LENGTH_SHORT).show()

                }


            }

            override fun onFailure(call: Call<defaultResponse>, t: Throwable) {
                Toast.makeText(this@Posttask, "Unable to process request", Toast.LENGTH_SHORT).show()
                finish()

            }

        }

        )

    }




    }











