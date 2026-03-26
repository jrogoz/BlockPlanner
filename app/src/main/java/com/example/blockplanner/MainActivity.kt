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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

import com.example.blockplanner.ui.theme.BlockPlannerTheme
import com.example.blockplanner.AppDatabase
import com.example.blockplanner.data.Rep
import com.example.blockplanner.data.User
import com.example.blockplanner.data.UserDao

import com.example.blockplanner.data.TimeBlock
import com.example.blockplanner.data.TimeBlockDao


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(android.R.layout.simple_list_item_1)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val userId = db.userDao().insert(User(
                username = "Asiek",
                password = "bardzosilnehaslo",
                email = "example@example.com"
                )).toInt()

            db.timeBlockDao().insert(TimeBlock(
                userId = userId,
                title = "obiad",
                dateStart = "02.04.26r.",
                dateStop = "None",
                timeStart = "9:00",
                timeStop = "10:00",
                rep = Rep.NONE
            ))

        }

        lifecycleScope.launch {
            val users = db.userDao().getAllUsers()
            Log.d("RoomTest", "Users: $users")
        }

        setContent {
            BlockPlannerTheme {
                Column{
                    DailyScreen(db.timeBlockDao())
                    UserListScreen(db.userDao())
                }
            }
        }
    }
}

@Composable
fun DailyScreen(timeBlockDao: TimeBlockDao) {

    var blocks by remember { mutableStateOf(listOf<TimeBlock>()) }

    LaunchedEffect(Unit) {
        blocks = timeBlockDao.getAllTimeBlocks()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Dzisiejsze bloki", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        blocks.forEach {
            TimeBlockCard(
                TimeBlockUi(
                    title = it.title,
                    start = it.timeStart,
                    end = it.timeStop
                )
            )
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

@Composable
fun UserListScreen(userDao: UserDao) {
    var users by remember { mutableStateOf(listOf<User>()) }

    LaunchedEffect(Unit) {
        users = userDao.getAllUsers()
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(users) { user ->
            Text(text = "${user.username}, ${user.email}")
        }
    }
}