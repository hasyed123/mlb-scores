package com.example.mlbscores.app.model.api

data class Probable(
    val abbreviation: String,
    val athlete: AthleteX,
    val displayName: String,
    val name: String,
    val playerId: Int,
    val shortDisplayName: String,
    val statistics: List<Any>
)