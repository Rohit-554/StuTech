package com.example.stutech

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stutech.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.email_id
import kotlinx.android.synthetic.main.login.register
import kotlinx.android.synthetic.main.register.*


class SignInActivity: AppCompatActivity() {
    lateinit var fAuth:FirebaseAuth
    lateinit var documentReference:DocumentReference
    lateinit var fStore:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_btn.setOnClickListener {
            if((email_id.text.toString()).isEmpty()){
                email_id.error = "Email Cannot be empty"
            }
            else if((loginpassword.text.toString()).isEmpty()){
                loginpassword.error = "Password Cannot be empty"
            }

             fAuth.signInWithEmailAndPassword(email_id.text.toString(),loginpassword.text.toString())
                 .addOnSuccessListener { task->
                     Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                     task.user?.let {
                             it1 ->
                         documentReference = fStore.collection("Users").document(it1.uid)
                     }
                     Log.d("Aman","$documentReference")
                     documentReference.get().addOnSuccessListener {
                         //normal user
                         if(it.getString("isAdmin")!="0"){
                             startActivity(Intent(this,Admin::class.java))
                         }
                         else{
                             startActivity(Intent(this,UserHome::class.java))
                         }
                     }
                 }.addOnFailureListener {

                 }





        }

    }


}