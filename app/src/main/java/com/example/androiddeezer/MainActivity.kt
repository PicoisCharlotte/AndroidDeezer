package com.example.androiddeezer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.androiddeezer.fragments.ListAlbumsFragment

class MainActivity : AppCompatActivity() {
    //private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val albumFragment = ListAlbumsFragment.newInstance()
        openFragment(albumFragment)

        val button = findViewById(R.id.playFab)
        button.setOnClickListener {onPlay()}

        NotificationGenerator.launchNotifManager(applicationContext);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            notificationManager =
//                getSystemService(
//                    Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            createNotificationChannel(
//                "com.example.androiddeezer.player",
//                "Player",
//                "Player Android Deezer")
//         }
    }

    private fun openFragment(fragment: ListAlbumsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onPlay(){
        NotificationGenerator.launchNotif(applicationContext, "Titre",  R.drawable.ic_action_pause)

    }




}


