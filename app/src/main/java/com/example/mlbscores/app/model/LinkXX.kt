package com.example.mlbscores.app.model

data class LinkXX(
    val href: String,
    val isExternal: Boolean,
    val isPremium: Boolean,
    val rel: List<String>,
    val text: String
)