package com.example.androiddeezer

import android.app.Notification
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.androiddeezer.fragments.ListAlbumsFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val albumFragment = ListAlbumsFragment.newInstance()
        openFragment(albumFragment)

        val button = findViewById(R.id.playFab)
        val textSongName = findViewById(R.id.textSongName)
        button.setOnClickListener {onPlay()}
        //createNotificationChannel();
    }

    private fun openFragment(fragment: ListAlbumsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onPlay(){
        Log.i("heyhey", "yoyo")
        //NotificationGenerator.openActivityNotification(applicationContext)
        NotificationGenerator.customBigNotification(applicationContext)
    }




}


