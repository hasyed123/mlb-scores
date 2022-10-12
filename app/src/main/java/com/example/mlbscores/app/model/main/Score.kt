package com.example.mlbscores.app.model.main

data class Score(
    val team1: String,
    val team2: String,
    val runners: Runners?,
    val inning: Int,
    val completed: Boolean,
    val topBottom: InningStatus
    )
