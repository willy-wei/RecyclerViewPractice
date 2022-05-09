package com.example.recyclerviewpractice

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.core.internal.view.SupportMenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.json.ItemData
import com.example.recyclerviewpractice.json.JsonToConvert
import com.example.recyclerviewpractice.json.ResultX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.CoroutineContext


class ListFragment : Fragment(){
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var communicator:Communicator
    private lateinit var adapter: RecyclerAdapter
    private var recyclerView: RecyclerView? = null
    private val zooUrl =
        URL("https://data.taipei/api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
    private val TAG = "INFO"
    //private var zooInfoList: List<JsonConvert> = mutableListOf()
    //private var zooFirst: FirstConvert? = null
    private var zooList: List<ItemData> = mutableListOf()

    var stringJson: String? = null
    var gsonBuilder = GsonBuilder()
    val gson = gsonBuilder.create()
    var zooResult:JsonToConvert? = null

    val myScope = object:CoroutineScope{
        override val coroutineContext: CoroutineContext
            get() = Job()
    }
    companion object{
        var strs:String? = null
        var jsonStr:Deferred<String?>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "onCreate")
        var client = OkHttpClient.Builder().build()
        var request = Request.Builder()
            .url(zooUrl)
            .build()

        jsonStr =  ZooResult(client, request)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        runBlocking {
            strs = jsonStr!!.await().toString()
        }
        Log.d("TAG", strs!!)
        if(strs == null)
            return null
        zooResult = Gson().fromJson(strs, object :TypeToken<JsonToConvert>(){}.type)
        Log.d("TAG", zooResult.toString())

        recyclerView = view.findViewById(R.id.list_view)
        (activity as AppCompatActivity).supportActionBar?.title = "臺北市立動物園"

        layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        adapter = RecyclerAdapter(zooResult!!.result.results, strs!!)
        recyclerView!!.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun ZooResult(client:OkHttpClient, request: Request) =
        CoroutineScope(Dispatchers.IO).async{
            var respone = client.newCall(request)
                .execute()

            stringJson = respone.body?.string()

            return@async stringJson
    }


}

