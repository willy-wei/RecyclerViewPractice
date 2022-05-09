package com.example.recyclerviewpractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.json.ItemData
import com.example.recyclerviewpractice.json.ResultX

class ItemFragment: Fragment(){

    private var columnCount = 1
    lateinit var imageView: ImageView
    lateinit var itemContent: TextView
    lateinit var itemTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.list_fragment, container, false)
        val itemList:MutableList<ResultX> = ArrayList()

        if(view is RecyclerView){
            with(view){
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                //adapter = RecyclerAdapter(itemList,view.toString())
            }
        }


        return view
    }



    companion object{
        const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}