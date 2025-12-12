package com.example.blockplanner.data

import androidx.room.*

@Dao
interface TimeBlockDao {
    @Insert
    suspend fun insert(timeBlock: TimeBlock)

    @Query("SELECT * FROM time_blocks")
    suspend fun getAllTimeBlocks(): List<TimeBlock>
}