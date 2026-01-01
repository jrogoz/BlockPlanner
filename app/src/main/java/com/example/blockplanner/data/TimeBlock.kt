package com.example.blockplanner.data

import androidx.room.*

@Entity(tableName = "time_blocks",
    foreignKeys = [
        ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
        )],
    indices = [Index("userId")]
)
data class TimeBlock(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val userId: Int,
    val dateStart: String,
    val dateStop: String,
    val timeStart: String,
    val timeStop: String,
    val rep: Rep,
    // TODO: val categoryId: Category (enum)
)