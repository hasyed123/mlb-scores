package com.example.mlbscores.app

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mlbscores.app.model.api.Event
import com.example.mlbscores.app.model.main.InningStatus
import com.example.mlbscores.app.model.main.Runners
import com.example.mlbscores.app.model.main.Score
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
        viewModel.startTracking()
        setContent {
            Screen()
        }
    }

}

@Composable
fun Screen() {
    val viewModel: MainViewModel = viewModel()
    val scoreboard = viewModel.scoreboard.observeAsState()
    val date = viewModel.date.observeAsState()
    val liveScores = viewModel.liveScores.observeAsState()

    LazyColumn {
        item {
            DatePicker(date.value ?: Date())
        }

        val events = scoreboard.value?.events
        items(events.orEmpty()) { event ->
            ScoreCard(event = event, liveScores = liveScores)
        }
    }
}

@Composable
fun DatePicker(date: Date) {
    val mContext = LocalContext.current
    val viewModel: MainViewModel = viewModel()

    val mDatePickerDialog = date.let {
        DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                viewModel.setDate(Date(mYear - 1900, mMonth, mDayOfMonth))
            }, it.year + 1900, it.month, it.date
        )
    }

    val mDate = SimpleDateFormat("yyyy/MM/dd").format(date)
    Button(onClick = {
        mDatePickerDialog.show()
    }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))) {
        Text(text = mDate, color = Color.White)
    }

}

@Composable
fun ScoreCard(event: Event, liveScores: State<java.util.HashMap<String, Score>?>) {
    val logo1 = event.competitions[0].competitors[0].team.logo
    val logo2 = event.competitions[0].competitors[1].team.logo
    val score: Score? = when (event.status.type.id == "2") {
        true -> {
            liveScores.value?.get(event.id)
        }
        else -> {
            Score(
                team1 = event.competitions[0].competitors[0].score,
                team2 = event.competitions[0].competitors[1].score,
                completed = true,
                inning = null,
                runners = null,
                topBottom = null,
                balls = null,
                strikes = null,
                outs = null
            )
        }
    }

    Row {
        TeamCard(
            teamScore = score?.team1,
            logo = logo1
        )
        TeamCard(
            teamScore = score?.team2,
            logo = logo2
        )
        score?.runners?.let {
            Image(
                painter = painterResource(id = getImageId(it)),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        }
        score?.inning?.let {
            Text(text = it.toString())
            score.topBottom?.let {
                val text = if (it == InningStatus.TOP) "Top "
                else "Bottom "
                Text(text = text)
            }
        }
        score?.balls?.let {
            Text(
                text = "$it-"
            )
        }
        score?.strikes?.let {
            Text(
                text = "$it"
            )
        }
        score?.outs?.let {
            Text(
                text = " $it outs"
            )
        }
    }

}

@Composable
fun TeamCard(teamScore: String?, logo: String) {
    Column {
        Image(
            painter = rememberAsyncImagePainter(logo),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        teamScore?.let {
            Text(
                text = it
            )
        }
    }
}

fun getImageId(runners: Runners): Int {
    var id: Int
    if (!runners.first && !runners.second && !runners.third) {
        id = com.example.mlbscores.R.drawable.empty
    }
    else if (runners.first && !runners.second && !runners.third){
        id = com.example.mlbscores.R.drawable.first
    }
    else if (runners.first && runners.second && !runners.third){
        id = com.example.mlbscores.R.drawable.first_and_second
    }
    else if (runners.first && !runners.second && runners.third){
        id = com.example.mlbscores.R.drawable.first_third
    }
    else if (!runners.first && runners.second && runners.third){
        id = com.example.mlbscores.R.drawable.second_third
    }
    else if (!runners.first && runners.second && !runners.third){
        id = com.example.mlbscores.R.drawable.second
    }
    else if (!runners.first && !runners.second && runners.third){
        id = com.example.mlbscores.R.drawable.third
    }
    else
        id = com.example.mlbscores.R.drawable.first_second_third

    return id
}
