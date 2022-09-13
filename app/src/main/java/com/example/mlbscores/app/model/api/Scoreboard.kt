package com.example.mlbscores.app.model.api

data class Scoreboard(
    val day: Day,
    val events: List<Event>,
    val leagues: List<League>,
    val season: SeasonXX
)