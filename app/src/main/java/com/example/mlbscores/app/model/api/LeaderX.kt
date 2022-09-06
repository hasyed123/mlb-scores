package com.example.mlbscores.app.model.api

data class LeaderX(
    val athlete: Athlete,
    val displayValue: String,
    val team: TeamX,
    val value: Double
)