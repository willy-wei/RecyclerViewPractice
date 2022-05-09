package com.example.recyclerviewpractice.json

data class Result(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<ResultX>,
    val sort: String
)