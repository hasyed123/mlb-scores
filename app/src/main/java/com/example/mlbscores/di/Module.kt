package com.example.mlbscores.di

import com.example.mlbscores.network.ScoresAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun providesScoresAPI(): ScoresAPI{
        return Retrofit.Builder()
            .baseUrl("http://site.api.espn.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScoresAPI::class.java)
    }
}