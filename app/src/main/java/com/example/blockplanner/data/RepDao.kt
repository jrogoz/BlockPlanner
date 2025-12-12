package com.example.blockplanner.data

import androidx.room.*

@Dao
interface RepDao {
    @Insert
    suspend fun insert(rep: Rep)
}