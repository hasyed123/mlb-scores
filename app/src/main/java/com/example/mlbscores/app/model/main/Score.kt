package com.example.mlbscores.app.model.main

data class Score(
    val team1: String,
    val team2: String,
    val completed: Boolean,
    val inning: Int?,
    val runners: Runners?,
    val topBottom: InningStatus?,
    val balls: Int?,
    val strikes: Int?,
    val outs: Int?
    )
