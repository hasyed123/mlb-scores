package com.example.mlbscores.app.model.api

data class Situation(
    val balls: Int,
    val batter: Batter,
    val lastPlay: LastPlay,
    val onFirst: Boolean,
    val onSecond: Boolean,
    val onThird: Boolean,
    val outs: Int,
    val pitcher: Pitcher,
    val strikes: Int
)