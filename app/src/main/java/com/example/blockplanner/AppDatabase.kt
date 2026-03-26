package com.example.blockplanner

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.blockplanner.data.Converts

import com.example.blockplanner.data.User
import com.example.blockplanner.data.UserDao
import com.example.blockplanner.data.Rep
import com.example.blockplanner.data.TimeBlock
import com.example.blockplanner.data.TimeBlockDao

@Database(entities = [User::class, TimeBlock::class], version = 3, exportSchema = false)
@TypeConverters(Converts::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
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
