package com.example.mlbscores.app.model.api

data class Venue(
    val address: Address,
    val capacity: Int,
    val fullName: String,
    val id: String,
    val indoor: Boolean
)