package com.example.mlbscores.app.model.api

data class AthleteXXX(
    val displayName: String,
    val fullName: String,
    val headshot: String,
    val id: String,
    val jersey: String,
    val links: List<Link>,
    val position: String,
    val shortName: String,
    val team: TeamX
)