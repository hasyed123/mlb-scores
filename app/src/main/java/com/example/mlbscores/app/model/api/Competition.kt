package com.example.mlbscores.app.model.api

data class Competition(
    val attendance: Int,
    val broadcasts: List<Broadcast>,
    val competitors: List<Competitor>,
    val conferenceCompetition: Boolean,
    val date: String,
    val format: Format,
    val geoBroadcasts: List<GeoBroadcast>,
    val id: String,
    val leaders: List<LeaderXX>,
    val neutralSite: Boolean,
    val notes: List<Note>,
    val outsText: String,
    val recent: Boolean,
    val situation: Situation,
    val startDate: String,
    val status: Status,
    val timeValid: Boolean,
    val type: TypeXXX,
    val uid: String,
    val venue: Venue,
    val wasSuspended: Boolean
)