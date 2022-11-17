package com.example.stutech

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.setViewTreeOnBackPressedDispatcherOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.view.*
import kotlinx.android.synthetic.main.register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var fAuth: FirebaseAuth
    lateinit var fStore:FirebaseFirestore
    lateinit var documentReference:DocumentReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        login.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        registerbtn.setOnClickListener {
            if((registeremail.text.toString()).isEmpty()){
                registeremail.error = "Email Cannot be empty"
            }
            if((regpassword.text.toString()).isEmpty()){
                regpassword.error = "Password Cannot be empty"
            }
            if((configPass.text.toString()).isEmpty()){
                configPass.error = "Cannot be empty"
            }
            userRegister()

        }

    }
    fun userRegister(){
        fAuth.createUserWithEmailAndPassword(registeremail.text.toString(),regpassword.text.toString())
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    val currentUser = fAuth.currentUser
                    Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                    if (currentUser != null) {
                        documentReference = fStore.collection("Users").document(currentUser.uid)
                    }
                    val userInfo: MutableMap<String, Any> = HashMap()
                    userInfo["Email"] = registeremail.text.toString()
                    if(tv_check.isChecked){
                        userInfo["isAdmin"] = "1"
                    }else{
                        userInfo["isAdmin"] = "0"
                    }

                    documentReference.set(userInfo).addOnSuccessListener {
                        Log.d(TAG, "User data for ${registeremail.text.toString()} was collected successfully ")
                    }.addOnFailureListener{ e ->
                        Log.w(TAG, "Error adding document", e)
                    }
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }

            }.addOnFailureListener { task->
                Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show()
            }
    }

}