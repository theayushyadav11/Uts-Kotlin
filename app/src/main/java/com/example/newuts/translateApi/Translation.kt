package com.example.newuts.translateApi

data class Translation(
    val source: String,
    val target: String,
    val text: String,
    val proxies: List<Any> = emptyList()
)