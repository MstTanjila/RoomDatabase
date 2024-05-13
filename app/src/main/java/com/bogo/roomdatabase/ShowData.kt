package com.bogo.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bogo.roomdashowdata.ViewAdapter
import com.bogo.roomdatabase.databinding.ActivityShowDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowData : AppCompatActivity() {
    lateinit var binding: ActivityShowDataBinding
    lateinit var studentDB:StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityShowDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        studentDB = StudentDatabase.getDatabase(this)
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            val studentList = studentDB.studentDao().getAll()
            val allListShowData = studentList.map{
                Student(id = 0, FirstName = it.FirstName, Lastname = it.Lastname, rollno = it.rollno.toInt())
            }
            val adapter = ViewAdapter(allListShowData)
            binding.recycleView.adapter = adapter
        }

    }
}