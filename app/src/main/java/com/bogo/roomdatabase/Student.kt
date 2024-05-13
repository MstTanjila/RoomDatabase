package com.bogo.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Student_Table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "first_name")
    val FirstName:String,
    @ColumnInfo(name = "last_name")
    val Lastname:String,
    @ColumnInfo(name = "roll_no")
    val rollno:Int
)
