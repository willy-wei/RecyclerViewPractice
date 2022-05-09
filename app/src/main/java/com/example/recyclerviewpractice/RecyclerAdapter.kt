package com.example.recyclerviewpractice

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.json.ItemData
import com.example.recyclerviewpractice.json.ResultX
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.InputStream
import java.lang.Exception

class RecyclerAdapter(
    private val values:List<ResultX>,
    json:String
):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    private lateinit var item:ResultX
    private val jsonString= json
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment, parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        item = values[position]
        holder.itemTextView.text = item.E_Name
        holder.itemContent.text = item.E_Info

        Picasso.get()
            .load(item.E_Pic_URL)
            .into(holder.itemImage)
        /*holder.itemView.setOnClickListener {
            View.OnClickListener { v ->
                /*if(position == 1) {
                    val activity = v!!.context as AppCompatActivity
                    val ContentFrag = ContentFragment()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.constraintlayout, ContentFrag).addToBackStack(null).commit()
                }*/
                Toast.makeText(v.context, "HELLO",Toast.LENGTH_LONG).show()
            }

        }*/
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTextView:TextView
        var itemContent:TextView

        init {
            itemImage = itemView.findViewById(R.id.imageView)
            itemTextView = itemView.findViewById(R.id.plant_title)
            itemContent = itemView.findViewById(R.id.content_text)

            itemView.setOnClickListener {
                val bundle = Bundle()
                val activity = it!!.context as AppCompatActivity
                val ContentFrag = ContentFragment()

                bundle.putString("message", jsonString)
                bundle.putInt("position", absoluteAdapterPosition)
                ContentFrag.arguments = bundle
                Log.d("TAGRE",jsonString)
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ContentFrag).addToBackStack(null).commit()
            }
        }
    }
}