package com.example.blockplanner.data

import androidx.room.*

@Entity(tableName = "time_blocks",
    foreignKeys = [
        ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
        entity = Rep::class,
        parentColumns = ["id"],
        childColumns = ["repId"],
        onDelete = ForeignKey.CASCADE
        )],
    indices = [Index("userId")]
)
data class TimeBlock(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val userId: Int,
    val date_start: String,
    val date_stop: String,
    val time_start: String,
    val time_stop: String,
    val repId: Int,
    // TODO: val categoryId: Int
)