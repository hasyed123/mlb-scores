package com.example.mlbscores.app.model

data class Probable(
    val abbreviation: String,
    val athlete: AthleteX,
    val displayName: String,
    val name: String,
    val playerId: Int,
    val shortDisplayName: String,
    val statistics: List<Any>
)