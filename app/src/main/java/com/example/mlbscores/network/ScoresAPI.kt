package com.example.mlbscores.network

import com.example.mlbscores.app.model.api.Scoreboard
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScoresAPI {

    @GET("/apis/site/v2/sports/baseball/mlb/scoreboard")
    suspend fun getScoreboard(
        @Query("dates")
        date: String? = null
    ): Response<Scoreboard>


}