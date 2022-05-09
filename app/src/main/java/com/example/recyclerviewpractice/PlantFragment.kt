package com.example.recyclerviewpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewpractice.json.PlantJsonConverter
import com.example.recyclerviewpractice.json.ResultXXX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: Int?  = 0
    private lateinit var plantImg:ImageView
    private lateinit var plantNameCh: TextView
    private lateinit var plantNameEn: TextView
    private lateinit var plantAlsoKnown: TextView
    private lateinit var plantBrief: TextView
    private lateinit var plantMethod: TextView
    private lateinit var plantFeature: TextView
    private lateinit var finalDate: TextView
    private lateinit var plantResult:PlantJsonConverter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("message")
            param2 = it.getInt("position")
        }
        Log.d("PlantFragment", param1.toString())
        plantResult = Gson().fromJson(param1, object : TypeToken<List<ResultXXX>>(){}.type)
        Log.d("PlantFragment", plantResult.toString())
        (activity as AppCompatActivity).supportActionBar?.title = plantResult.result.results[param2!!].F_Name_Ch
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_plant, container, false)
        plantImg = view.findViewById(R.id.plant_img)
        plantNameCh = view.findViewById(R.id.plant_name_ch)
        plantNameEn = view.findViewById(R.id.plant_name_en)
        plantAlsoKnown = view.findViewById(R.id.also_known_name)
        plantBrief = view.findViewById(R.id.brief_content)
        plantMethod = view.findViewById(R.id.method_content)
        plantFeature = view.findViewById(R.id.features_content)
        finalDate = view.findViewById(R.id.finaltime)

        plantNameCh.text = plantResult.result.results[param2!!].F_Name_Ch
        plantNameEn.text = plantResult.result.results[param2!!].F_Name_En
        plantAlsoKnown.text = plantResult.result.results[param2!!].F_AlsoKnown
        plantBrief.text = plantResult.result.results[param2!!].F_Brief
        plantMethod.text = plantResult.result.results[param2!!].`F_Function＆Application`
        plantFeature.text = plantResult.result.results[param2!!].F_Feature
        finalDate.text = plantResult.result.results[param2!!].F_Update

        Picasso.get()
            .load(plantResult.result.results[param2!!].F_Pic01_URL)
            .into(plantImg)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}