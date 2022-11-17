package com.example.stutech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.facultylist.*
import kotlinx.android.synthetic.main.students.*

class UserHome : AppCompatActivity() {
    lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.students)
        fAuth = FirebaseAuth.getInstance()
        userLogout.setOnClickListener {
            fAuth.signOut()
            startActivity(Intent((this),MainActivity::class.java))
            finish()
        }
        syll.setOnClickListener {
            startActivity(Intent((this),Syllabus::class.java))
        }
        Flist1.setOnClickListener {
            startActivity(Intent((this),FacultyList::class.java))
        }
        Attend1.setOnClickListener {
            startActivity(Intent((this),Attendance::class.java))
        }
        Marks1.setOnClickListener {
            startActivity(Intent((this),Marks::class.java))
        }
        Lab1.setOnClickListener {
            startActivity(Intent((this),Labs::class.java))
        }
        QBank1.setOnClickListener {
            startActivity(Intent((this),QuestionBank::class.java))
        }
        books1.setOnClickListener {
            startActivity(Intent((this),Books::class.java))
        }

    }
}