package com.example.recyclerviewpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.json.ItemData
import com.example.recyclerviewpractice.json.ResultX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity : AppCompatActivity(),Communicator {
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = ListFragment()
        val toolbar:Toolbar = findViewById(R.id.toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.toolbar_title)
    }

    override fun passDataCom(fragmentName: String, jsonString: String) {
        var bundle = Bundle()
        bundle.putString("message", jsonString)

        //val manager = supportFragmentManager
        val transation = this.supportFragmentManager.beginTransaction()
        val listFragment = this.supportFragmentManager.findFragmentByTag(fragmentName)

        listFragment?.arguments = bundle
        transation.replace(R.id.fragment_container, listFragment!!)
        transation.commit()
    }
}