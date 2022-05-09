package com.example.recyclerviewpractice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.json.JsonToConvert
import com.example.recyclerviewpractice.json.PlantJsonConverter
import com.example.recyclerviewpractice.json.ResultX
import com.example.recyclerviewpractice.json.ResultXXX
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URI
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: Int? = null
    private var zooResult: JsonToConvert? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: PlantRecyclerAdapter
    private var plantView: RecyclerView? = null
    private var animalView: ImageView? = null
    private var animalTitle:TextView? = null
    private var animalContent:TextView? = null
    private var animalButton:Button? = null
    private var animalDate:TextView? = null
    private var animalLocation:TextView? = null
    private val plantUrl = URL("https://data.taipei/api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
    private var plantResult: PlantJsonConverter? = null
    var stringJson: String? = null
    private var strs:String? = null
    private var jsonStr: Deferred<String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var client = OkHttpClient.Builder().build()
        var request = Request.Builder()
            .url(plantUrl)
            .build()
        jsonStr =  PlantResult(client, request)
        arguments?.let {
            param1 = it.getString("message")
            param2 = it.getInt("position")
        }
        zooResult = Gson().fromJson(param1, object : TypeToken<JsonToConvert>(){}.type)
        (activity as AppCompatActivity).supportActionBar?.title = zooResult!!.result.results[param2!!].E_Name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        animalView = view.findViewById(R.id.img)
        animalContent = view.findViewById(R.id.content_id)
        animalButton = view.findViewById(R.id.page_button)
        animalDate = view.findViewById(R.id.date_id)
        animalLocation = view.findViewById(R.id.location_id)
        runBlocking {
            strs = jsonStr!!.await().toString()
            Log.d("TAG1", strs!!)
        }

        Log.d("ContentPlant", strs!!)

        plantResult = Gson().fromJson(strs!!, object :TypeToken<PlantJsonConverter>(){}.type)
        Log.d("ContentPlant", plantResult!!.result.results[0].F_Name_Ch
            .toString())

        animalContent!!.text = zooResult!!.result.results[param2!!].E_Info
        animalDate!!.text = zooResult!!.result.results[param2!!].E_Memo
        animalLocation!!.text = zooResult!!.result.results[param2!!].E_Category
        Picasso.get()
            .load(zooResult!!.result.results[param2!!].E_Pic_URL)
            .into(animalView)
        plantView = view.findViewById(R.id.plant_id)
        var locationList:MutableList<ResultXXX> = mutableListOf<ResultXXX>()
        for(i in plantResult!!.result.results){
            Log.d("content1", i.F_Location!!)
            var category = i.F_Location!!.split("；")
            for(cate in category){
                Log.d("content2", cate)
                if(cate == zooResult!!.result.results[param2!!].E_Name){
                    locationList.add(i)
                }
            }
        }
        layoutManager = LinearLayoutManager(context)
        plantView!!.layoutManager = layoutManager
        adapter = PlantRecyclerAdapter(locationList, strs!!)
        plantView!!.adapter = adapter

        animalButton!!.setOnClickListener {
            val uri: Uri = Uri.parse(zooResult!!.result.results[param2!!].E_URL)
            val intent: Intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }
    fun PlantResult(client:OkHttpClient,request: Request) =
        CoroutineScope(Dispatchers.IO).async{
            var respone = client.newCall(request).execute()
            stringJson = respone.body?.string()
            return@async stringJson
        }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}