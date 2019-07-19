package com.example.androiddeezer

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.RemoteViews

object NotificationGenerator {
    val NOTIFY_PREVIOUS = "com.example.androiddeezer.previous"
    val NOTIFY_DELETE = "com.example.androiddeezer.delete"
    val NOTIFY_PAUSE = "com.example.androiddeezer.pause"
    val NOTIFY_PLAY = "com.example.androiddeezer.play"
    val NOTIFY_NEXT = "com.example.androiddeezer.next"

    private val NOTIFICATION_ID_CUSTOM_BIG = 9

    fun customBigNotification(context: Context, titre: String, album_art: Int) {
        val expandedView = RemoteViews(context.packageName, R.layout.big_notification)

        val nc = NotificationCompat.Builder(context)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyIntent = Intent(context, MainActivity::class.java)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        nc.setContentIntent(pendingIntent)
        nc.setSmallIcon(R.drawable.ic_action_play)
        nc.setAutoCancel(true)

        expandedView.setTextViewText(R.id.textSongName, titre)
        expandedView.setImageViewResource(R.id.album_art, album_art)
        nc.setCustomBigContentView(expandedView)

        setListeners(expandedView, context)

        nm.notify(NOTIFICATION_ID_CUSTOM_BIG, nc.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun newNotif(notificationManager: NotificationManager?, context: Context, titre: String, album_art: Int){

        val notificationID = 101

        Log.i("Axel", "lol")
        val channelID = "com.example.androiddeezer.player"

        val notification = Notification.Builder(context,
            channelID)
            .setContentTitle("Example Notification")
            .setContentText("This is an  example notification.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setChannelId(channelID)
            .build()

        notificationManager?.notify(notificationID, notification)
    }

    private fun setListeners(view: RemoteViews, context: Context) {
        val previous = Intent(NOTIFY_PREVIOUS)
        val delete = Intent(NOTIFY_DELETE)
        val pause = Intent(NOTIFY_PAUSE)
        val next = Intent(NOTIFY_NEXT)
        val play = Intent(NOTIFY_PLAY)

        val pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnPrevious, pPrevious)


        val pDelete = PendingIntent.getBroadcast(context, 0, delete, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnDelete, pDelete)


        val pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnPause, pPause)


        val pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnNext, pNext)


        val pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnPlay, pPlay)

    }

}
