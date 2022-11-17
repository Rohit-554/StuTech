package com.example.stutech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stutech.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var fAuth:FirebaseAuth
    lateinit var fStore:FirebaseFirestore
    lateinit var documentReference: DocumentReference
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fStore = FirebaseFirestore.getInstance()
        setContentView(binding.root)
        fAuth = FirebaseAuth.getInstance()
        binding.cseLogin.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        binding.adminLogin.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        if (fAuth.currentUser!=null){
            documentReference = fStore.collection("Users").document(fAuth.currentUser!!.uid)
            documentReference.get().addOnSuccessListener {
                //normal user
                if(it.getString("isAdmin")!="0"){
                    startActivity(Intent(this,Admin::class.java))
                    finish()
                }
                else{
                    startActivity(Intent(this,UserHome::class.java))
                    finish()
                }
            }.addOnFailureListener {
                fAuth.signOut()
            }
        }
    }

}