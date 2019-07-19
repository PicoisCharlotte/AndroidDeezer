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
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val albumFragment = ListAlbumsFragment.newInstance()
        openFragment(albumFragment)

        val button = findViewById(R.id.playFab)
        button.setOnClickListener {onPlay()}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager =
                getSystemService(
                    Context.NOTIFICATION_SERVICE) as NotificationManager

            createNotificationChannel(
                "com.example.androiddezer.player",
                "Player",
                "Player Android Deezer")
         }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String,
                                          description: String) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }

    private fun openFragment(fragment: ListAlbumsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onPlay(){
        //NotificationGenerator.openActivityNotification(applicationContext)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationGenerator.newNotif(notificationManager, applicationContext, "Titre",  R.drawable.ic_action_pause)
        } else
            NotificationGenerator.customBigNotification(applicationContext, "Titre",  R.drawable.ic_action_pause)
    }




}


