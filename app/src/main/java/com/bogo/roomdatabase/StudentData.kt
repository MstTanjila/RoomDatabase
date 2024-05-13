package com.bogo.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [Student::class], version = 1)
 abstract class StudentDatabase:RoomDatabase() {
     abstract fun studentDao():StudentDao
     companion object{
         @Volatile
         private var INSTANCE :StudentDatabase?=null
         fun getDatabase(context:Context):StudentDatabase {
             val tempInstatance= INSTANCE
             if (tempInstatance!=null){
                 return tempInstatance
             }
             synchronized(this){
                 val instance=Room.databaseBuilder(
                     context.applicationContext,
                     StudentDatabase::class.java,
                     "student_database"
                 ).build()
             INSTANCE=instance
             return instance}
         }
     }
}