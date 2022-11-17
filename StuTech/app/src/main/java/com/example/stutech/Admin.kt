package com.example.stutech

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_admin.*


class Admin : AppCompatActivity() {
    lateinit var fAuth:FirebaseAuth
    lateinit var uri:Uri
    lateinit var mStorage : StorageReference
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        fAuth = FirebaseAuth.getInstance()
        mStorage = FirebaseStorage.getInstance().getReference("Uploads")
        admin_logout.setOnClickListener {
            fAuth.signOut()
            startActivity(Intent((this),MainActivity::class.java))
            finish()
        }
        UploadSyllabus.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "application/pdf"
            getResult.launch(galleryIntent)
        }


    }
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            Log.d("xxx","${it.resultCode}")
            if(it.resultCode == Activity.RESULT_OK){
                uri= it.data?.data!!
                upload()
            }
        }

    private fun upload(){
        val mReference = uri.lastPathSegment?.let { mStorage.child(it) }
        try {
            mReference?.putFile(uri)?.addOnCompleteListener {
                val url = mReference.downloadUrl
                Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

}