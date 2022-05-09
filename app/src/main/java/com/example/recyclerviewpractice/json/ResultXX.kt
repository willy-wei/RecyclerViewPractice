package com.example.recyclerviewpractice.json

data class ResultXX(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<ResultXXX>,
    val sort: String
)