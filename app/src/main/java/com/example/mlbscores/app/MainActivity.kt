package com.example.mlbscores.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mlbscores.app.model.Event
import com.example.mlbscores.databinding.ActivityMainBinding
import com.example.mlbscores.app.model.Scoreboard
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private var scoreboard: Scoreboard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getScores()
        binding.composeView.setContent {
            Screen()
        }
    }

}

@Composable
fun Screen() {
    val viewModel: MainViewModel = viewModel()
    val scoreboard = viewModel.scoreboard.observeAsState()
    LazyColumn {
        val events = scoreboard.value?.events
        items(events.orEmpty()) { event ->
            val logo1 = event.competitions[0].competitors[0].team.logo
            val logo2 = event.competitions[0].competitors[1].team.logo
            Column {
                Text(
                    text = event.name,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                )

                Row{
                    Image(
                        painter = rememberAsyncImagePainter(logo1),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                    Image(
                        painter = rememberAsyncImagePainter(logo2),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                }
            }
        }
    }
}
