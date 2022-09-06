package com.example.mlbscores.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlbscores.app.model.api.Scoreboard
import com.example.mlbscores.network.ScoresAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val scoresAPI: ScoresAPI) : ViewModel() {
    private val _scoreboard = MutableLiveData<Scoreboard>()
    val scoreboard: LiveData<Scoreboard> = _scoreboard

    private val _date = MutableLiveData<Date>()
    val date: LiveData<Date> = _date

    private fun getScores() {
        viewModelScope.launch {
            val response = scoresAPI.getScoreboard(
                SimpleDateFormat("yyyyMMdd").format(_date.value)
            )
            if (response.isSuccessful) {
                _scoreboard.value = response.body()
            }
        }
    }

    fun setDate(date: Date) {
        _date.value = date
        getScores()
    }

    fun init() {
        if (_date.value == null) {
            setDate(Date())
        }
    }
}