package com.example.recyclerviewpractice

import com.example.recyclerviewpractice.json.ResultX

interface Communicator {
    fun passDataCom(fragmentName:String, jsonString:String)
}