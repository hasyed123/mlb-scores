package com.example.mlbscores.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlbscores.app.model.Scoreboard
import com.example.mlbscores.network.ScoresAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val scoresAPI: ScoresAPI): ViewModel() {
    private val _scoreboard = MutableLiveData<Scoreboard>()
    val scoreboard: LiveData<Scoreboard> = _scoreboard

    fun getScores(date: String? = null){
        viewModelScope.launch {
            val response = scoresAPI.getScoreboard(date)
            if(response.isSuccessful){
                _scoreboard.value = response.body()
            }
        }
    }
}