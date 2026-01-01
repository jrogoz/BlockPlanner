package com.example.blockplanner.data

import androidx.room.TypeConverter

class Converts {
    @TypeConverter
    fun fromRep(rep: Rep): String {
        return rep.name
    }

    @TypeConverter
    fun toRep(value: String): Rep {
        return Rep.valueOf(value)
    }

}