package com.example.blockplanner.data

import androidx.room.TypeConverter

class Converts {
    @TypeConverter
    fun fromRep(value: Rep): String {
        return value.name
    }

    @TypeConverter
    fun toRep(value: String): Rep {
        return Rep.valueOf(value)
    }

}