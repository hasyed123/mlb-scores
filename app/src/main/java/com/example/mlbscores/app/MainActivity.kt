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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mlbscores.app.model.main.Score
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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
            val logo1 = event.competitions[0].competitors[0].team.logo
            val logo2 = event.competitions[0].competitors[1].team.logo
            val score: Score? = when(viewModel.isCurrentDate()) {
                true -> {
                    liveScores.value?.get(event.id)
                }
                else -> {
                    Score(
                        team1 = event.competitions[0].competitors[0].score,
                        team2 = event.competitions[0].competitors[1].score
                    )
                }
            }

            Row {

                Column {
                    Image(
                        painter = rememberAsyncImagePainter(logo1),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                    score?.let {
                        Text(
                            text = it.team1
                        )
                    }
                }

                Column {
                    Image(
                        painter = rememberAsyncImagePainter(logo2),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                    score?.let {
                        Text(
                            text = it.team2
                        )
                    }
                }
            }
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
        mDatePickerDialog?.show()
    }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))) {
        Text(text = mDate, color = Color.White)
    }

}
