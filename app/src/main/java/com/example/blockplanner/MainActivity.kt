package com.example.blockplanner

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.blockplanner.ui.theme.BlockPlannerTheme
import com.example.blockplanner.AppDatabase
import com.example.blockplanner.User

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlockPlannerTheme {
                DailyScreen()
            }
        }

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        lifecycleScope.launch {
            userDao.insert(User(name = "Jan", age = 25))
        }

        lifecycleScope.launch {
            val users = userDao.getAllUsers()
            Log.d("RoomTest", "Users: $users")
        }
    }
}

@Composable
fun DailyScreen() {

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Dzisiejsze bloki", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        val exampleBlocks = listOf(
            TimeBlockUi("Czytanie", "08:00", "09:00"),
            TimeBlockUi("Śniadanie", "09:00", "10:00")
        )

        exampleBlocks.forEach {
            TimeBlockCard(it)
            Spacer(Modifier.height(8.dp))
        }
    }
}

data class TimeBlockUi(
    val title: String,
    val start: String,
    val end: String
)

@Composable
fun TimeBlockCard(block: TimeBlockUi) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(block.title, style = MaterialTheme.typography.titleMedium)
            Text("${block.start} – ${block.end}")
        }
    }
}