package com.example.lpuactivity.util.Personal_Post

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lpuactivity.R
import com.example.lpuactivity.models.Video1
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_recycle_home.view.*

class personal_post_adaptar(private val homefeed: List<Video1>):


    RecyclerView.Adapter<personal_post_adaptar.ExampleViewHolder>() {



    inner class ExampleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){




        init {
            itemView.setOnClickListener {
                val position:Int =adapterPosition
                val video=homefeed[position]
                var img=video.photos

                if(img.isEmpty())
                {
                    img="https://firebasestorage.googleapis.com/v0/b/virtusa-58806.appspot.com/o/images%2Fdefault-post-pic.png?alt=media&token=1459f509-aaa4-4f0e-a4f6-57331eaad74a"
                }else{
                    img=video.photos
                }
                val intent = Intent (itemView.context, Personal_post_detail::class.java)

                intent.putExtra("Title",video.title)
                intent.putExtra("description",video.description)
                intent.putExtra("Postby",video.postby)
                intent.putExtra("Image",img)
                intent.putExtra("Price",video.price)
                intent.putExtra("Description",video.description)
                intent.putExtra("Id",video.id)
                intent.putExtra("button_name","Remove")
                itemView.context.startActivity(intent)
                Toast.makeText(itemView.context, video.price,Toast.LENGTH_SHORT).show()

            }
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val cellofrow=layoutInflater.inflate(R.layout.fragment_recycle_home,parent,false)
        return ExampleViewHolder(cellofrow)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {

        val video=homefeed[position]
        holder.itemView.text_home.text=video.title
        val imageholder=holder.itemView.image_home

        var img1=video.photos

        img1 = if(img1.isEmpty()) {
            "https://firebasestorage.googleapis.com/v0/b/virtusa-58806.appspot.com/o/images%2Fdefault-post-pic.png?alt=media&token=1459f509-aaa4-4f0e-a4f6-57331eaad74a"
        }else{
            video.photos
        }
        Picasso.get().load(img1).into(imageholder)

    }

    override fun getItemCount(): Int {

        return homefeed.count()
    }


}