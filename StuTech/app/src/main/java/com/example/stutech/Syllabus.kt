package com.example.stutech

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.example.stutech.Syllabus
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.students.*
import kotlinx.android.synthetic.main.syllabus.*
import java.io.File

class Syllabus : AppCompatActivity() {
    lateinit var mStorage : StorageReference
    lateinit var downloadManager: DownloadManager
    lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syllabus)
        mStorage = FirebaseStorage.getInstance().getReference("Uploads")
        Sem1.setOnClickListener {
            val uri = mStorage.downloadUrl.toString()
            downloadFile(uri,"pdf")
        }
    }
    private fun downloadFile(uri:String,fileName:String){
        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val url = Uri.parse(uri)
        var request = DownloadManager.Request(url)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setMimeType("Pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(fileName)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,File.separator+fileName+".pdf")
        downloadManager.enqueue(request)
        Toast.makeText(this, "Image Downloaded ", Toast.LENGTH_SHORT).show()

    }
}