package com.example.androiddeezer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews

object NotificationGenerator {
    val NOTIFY_PREVIOUS = "com.example.androiddeezer.previous"
    val NOTIFY_DELETE = "com.example.androiddeezer.delete"
    val NOTIFY_PAUSE = "com.example.androiddeezer.pause"
    val NOTIFY_PLAY = "com.example.androiddeezer.play"
    val NOTIFY_NEXT = "com.example.androiddeezer.next"

    val pPause: PendingIntent? = null
    val pPlay: PendingIntent? = null
    val pNext: PendingIntent? = null
    val pPrevious: PendingIntent? = null

    private val NOTIFICATION_ID_CUSTOM_BIG = 9
    private var notificationManager: NotificationManager? = null


    fun launchNotifManager(context: Context){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager =
                context.getSystemService(
                    Context.NOTIFICATION_SERVICE) as NotificationManager

            createNotificationChannel(
                "com.example.androiddeezer.player",
                "Player",
                "Player Android Deezer")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(id: String, name: String,
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

    fun launchNotif(context: Context, titre: String, artist: String, album_art: Int){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newNotif(notificationManager, context, titre, artist, album_art)
        } else
            customBigNotification(context, titre, artist, album_art)
    }

    fun customBigNotification(context: Context, titre: String, artist: String, album_art: Int) {
        val expandedView = RemoteViews(context.packageName, R.layout.big_notification)

        val nc = NotificationCompat.Builder(context)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyIntent = Intent(context, MainActivity::class.java)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        nc.setContentIntent(pendingIntent)
        nc.setSmallIcon(R.drawable.ic_action_play)
        nc.setAutoCancel(true)
            .setContentIntent(pPause)
            .setContentIntent(pPlay)
            .setContentIntent(pNext)
            .setContentIntent(pPrevious)

        expandedView.setTextViewText(R.id.title_album_notif, titre)
        expandedView.setTextViewText(R.id.artist_notif, artist)
        expandedView.setImageViewResource(R.id.album_art_notif, album_art)
        nc.setCustomBigContentView(expandedView)

        setListeners(expandedView, context)

        nm.notify(NOTIFICATION_ID_CUSTOM_BIG, nc.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun newNotif(notificationManager: NotificationManager?, context: Context, titre: String, artist: String,  album_art: Int){

        val notificationID = 101

        val channelID = "com.example.androiddeezer.player"
        val expandedView = RemoteViews(context.packageName, R.layout.big_notification)
        expandedView.setTextViewText(R.id.title_album_notif, titre)
        expandedView.setTextViewText(R.id.artist_notif, artist)
        expandedView.setImageViewResource(R.id.album_art_notif, album_art)
        val notifyIntent = Intent(context, MainActivity::class.java)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = Notification.Builder(context,
            channelID)
            .setSmallIcon(R.drawable.ic_action_play)
            .setChannelId(channelID)
            .setCustomBigContentView(expandedView)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setContentIntent(pPause)
            .setContentIntent(pPlay)
            .setContentIntent(pNext)
            .setContentIntent(pPrevious)
            .build()

        setListeners(expandedView, context)

        notificationManager?.notify(notificationID, notification)
    }

    private fun setListeners(view: RemoteViews, context: Context) {
        val previous = Intent(NOTIFY_PREVIOUS)
        val pause = Intent(NOTIFY_PAUSE)
        val next = Intent(NOTIFY_NEXT)
        val play = Intent(NOTIFY_PLAY)

        val pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_previous_notif, pPrevious)

        val pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_pause_notif, pPause)


        val pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_next_notif, pNext)


        val pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_play_notif, pPlay)
    }

}
