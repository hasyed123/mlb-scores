package com.example.mlbscores.app.model

data class LastPlay(
    val atBatId: String,
    val athletesInvolved: List<AthletesInvolved>,
    val id: String,
    val scoreValue: Int,
    val summaryType: String,
    val team: TeamX,
    val text: String,
    val type: TypeX
)