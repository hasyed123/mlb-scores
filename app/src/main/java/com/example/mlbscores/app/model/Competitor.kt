package com.example.mlbscores.app.model

data class Competitor(
    val errors: Int,
    val hits: Int,
    val homeAway: String,
    val id: String,
    val leaders: List<Leader>,
    val linescores: List<Linescore>,
    val order: Int,
    val probables: List<Probable>,
    val score: String,
    val statistics: List<Statistic>,
    val team: TeamXXX,
    val type: String,
    val uid: String
)