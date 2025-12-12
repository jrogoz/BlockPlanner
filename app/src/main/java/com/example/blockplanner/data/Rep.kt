package com.example.blockplanner.data

import androidx.room.*

@Entity(tableName = "reps")
data class Rep(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val repType: String
)
