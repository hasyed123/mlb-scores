package com.example.mlbscores.app.model

data class GeoBroadcast(
    val lang: String,
    val market: Market,
    val media: Media,
    val region: String,
    val type: Type
)