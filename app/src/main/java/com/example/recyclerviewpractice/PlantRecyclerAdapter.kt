package com.example.recyclerviewpractice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.json.ResultXXX
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class PlantRecyclerAdapter(
    private val value: List<ResultXXX>,
    plantJson:String
): RecyclerView.Adapter<PlantRecyclerAdapter.PlantViewHolder>() {
    private lateinit var item: ResultXXX


    var _planJson :String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment, parent, false)
        _planJson = Gson().toJson(value)
        return PlantViewHolder(v)
    }

    override fun getItemCount(): Int {
        return value.size
    }
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        item = value[position]
        Log.d("Plant",item.toString())
        holder.plantTitle_ch.text = item.F_Name_Ch

        holder.planetContent.text = item.F_AlsoKnown
        Picasso.get()
            .load(item.F_Pic01_URL)
            .into(holder.plantImg)
    }

    inner class PlantViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var plantImg:ImageView
        var plantTitle_ch:TextView
        var planetContent:TextView
        init {
            plantImg = itemView.findViewById(R.id.imageView)
            plantTitle_ch = itemView.findViewById(R.id.plant_title)
            planetContent = itemView.findViewById(R.id.content_text)

            itemView.setOnClickListener{
                val bundle = Bundle()
                val activity = it!!.context as AppCompatActivity
                val plantFrag = PlantFragment()

                bundle.putString("message", _planJson)
                bundle.putInt("position", absoluteAdapterPosition)
                plantFrag.arguments = bundle
                Log.d("TAGRE",_planJson!!)
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, plantFrag).addToBackStack(null).commit()
            }
        }
    }

}