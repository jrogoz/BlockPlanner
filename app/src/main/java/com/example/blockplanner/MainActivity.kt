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
import com.example.blockplanner.data.User
import com.example.blockplanner.data.UserDao

import com.example.blockplanner.data.Rep
import com.example.blockplanner.data.RepDao

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

            db.repDao().insert(Rep(repType = "once"))
            db.repDao().insert(Rep(repType = "daily"))
            db.repDao().insert(Rep(repType = "weekly"))
            db.repDao().insert(Rep(repType = "custom"))

            db.timeBlockDao().insert(TimeBlock(
                userId = userId,
                date_start = "02.01.26r.",
                date_stop = "None",
                time_start = "9:00",
                time_stop = "10:00",
                repId = 1
            ))

//            val users = db.userDao().getAllUsers()
//            val createdUser = db.userDao().getUserById(userId)


        }

        lifecycleScope.launch {
            val users = db.userDao().getAllUsers()
            Log.d("RoomTest", "Users: $users")
        }

        setContent {
            BlockPlannerTheme {
                Column{
                    DailyScreen()
                    UserListScreen(db.userDao())
                }
            }
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