package com.example.blockplanner

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.blockplanner.data.User
import com.example.blockplanner.data.UserDao
import com.example.blockplanner.data.Rep
import com.example.blockplanner.data.RepDao
import com.example.blockplanner.data.TimeBlock
import com.example.blockplanner.data.TimeBlockDao

@Database(entities = [User::class, Rep::class, TimeBlock::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun repDao(): RepDao
    abstract fun timeBlockDao(): TimeBlockDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration() // TODO: change it for proper migration before production
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
