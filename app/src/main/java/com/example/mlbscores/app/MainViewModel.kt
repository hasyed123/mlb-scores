package com.example.mlbscores.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlbscores.app.model.api.Scoreboard
import com.example.mlbscores.app.model.main.InningStatus
import com.example.mlbscores.app.model.main.Runners
import com.example.mlbscores.app.model.main.Score
import com.example.mlbscores.network.ScoresAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class MainViewModel @Inject constructor(private val scoresAPI: ScoresAPI) : ViewModel() {
    private val _scoreboard = MutableLiveData<Scoreboard>()
    val scoreboard: LiveData<Scoreboard> = _scoreboard

    private val _date = MutableLiveData<Date>()
    val date: LiveData<Date> = _date

    private val _liveScores = MutableLiveData<HashMap<String, Score>>()
    val liveScores: LiveData<HashMap<String, Score>> = _liveScores

    private val currentDate = Date()

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

    private suspend fun getLiveScores() {
        val response = scoresAPI.getScoreboard(
            SimpleDateFormat("yyyyMMdd").format(currentDate)
        )
        if (response.isSuccessful) {
            val listOfScores = HashMap<String, Score>()
            response.body()?.events?.forEach {
                val newScore = Score(
                    team1 = it.competitions[0].competitors[0].score,
                    team2 = it.competitions[0].competitors[1].score,
                    runners = Runners(
                        first = it.competitions[0].situation?.onFirst ?: false,
                        second = it.competitions[0].situation?.onSecond ?: false,
                        third = it.competitions[0].situation?.onThird ?: false
                    ),
                    inning = it.status.period,
                    completed = it.status.type.completed,
                    topBottom = when(it.status.type.detail.contains("Top")) {
                        true -> InningStatus.TOP
                        else -> InningStatus.BOTTOM
                    }
                )
                listOfScores[it.id] = newScore
            }
            _liveScores.value = listOfScores
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

    fun isCurrentDate(): Boolean {
        return when {
            date.value?.year != currentDate.year -> false
            date.value?.month != currentDate.month -> false
            else -> date.value?.date == currentDate.date
        }
    }

    fun startTracking() {
        viewModelScope.launch {
            while (true) {
                getLiveScores()
                delay(5000)
            }
        }

    }

}