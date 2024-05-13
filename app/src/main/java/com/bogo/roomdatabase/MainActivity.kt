package com.bogo.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.get
import com.bogo.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var studentDatabase:StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        studentDatabase=StudentDatabase.getDatabase(this)
    binding.saveBtn.setOnClickListener {
        saveData()
    }
        binding.showBtn.setOnClickListener {
            startActivity(Intent(this,ShowData::class.java))
        }
        binding.searchBtn.setOnClickListener {
            searchData()
        }
        binding.dltallBtn.setOnClickListener {
            GlobalScope.launch {
                studentDatabase.studentDao().deleteAll()
            }
        }
    }

@OptIn(DelicateCoroutinesApi::class)
    private fun searchData() {
        val rollNo=binding.rollnoEt.text.toString()
        if (rollNo.isNotEmpty()){

            GlobalScope.launch{
                var student:Student =studentDatabase.studentDao().findById(rollNo.toInt())
                if (studentDatabase.studentDao().isEmpty()){
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(this@MainActivity, "No Data Found", Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    displayData(student)
                }
            }

        }

    }
    private suspend fun displayData(student: Student) {
        withContext(Dispatchers.Main){
            val fname = student?.FirstName.toString()
            val lname = student?.Lastname.toString()
            val roll = student?.rollno.toString()

            if (fname.isNullOrEmpty() || lname.isNullOrEmpty() || roll.isNullOrEmpty()) {
                binding.firstnameEt.setText("")
                binding.lastnameEt.setText("")
                binding.rollnoEt.setText("")
                Toast.makeText(this@MainActivity, "No data found", Toast.LENGTH_SHORT).show()
            }else{
                binding.firstnameEt.setText(fname)
                binding.lastnameEt.setText(lname)
                binding.rollnoEt.setText(roll)
            }
        }
    }

    private fun saveData() {
        val firstName=binding.firstnameEt.text.toString()
        val lastName=binding.lastnameEt.text.toString()
        val rollno=binding.rollEt.text.toString()
        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollno.isNotEmpty()) {
            val student=Student(0,firstName,lastName,rollno.toInt())
            GlobalScope.launch {
                studentDatabase.studentDao().insert(student)
            }
            binding.firstnameEt.text?.clear()
            binding.lastnameEt.text?.clear()
            binding.rollnoEt.text?.clear()
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()


        }else {
            Toast.makeText(this, "Fill All the feilds", Toast.LENGTH_SHORT).show()

        }

    }
}