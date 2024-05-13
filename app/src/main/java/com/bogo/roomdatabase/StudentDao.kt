package com.bogo.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT * FROM Student_Table ORDER BY id ASC")
    suspend fun getAll():List<Student>

    @Query("SELECT * FROM Student_Table WHERE roll_no = :roll")
    suspend fun findById(roll: Int): Student

    @Query("DELETE FROM Student_Table")
    suspend fun deleteAll()

    @Query("SELECT(SELECT COUNT(*) FROM Student_Table)==0")
    fun isEmpty(): Boolean
}