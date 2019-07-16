package com.example.androiddeezer

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews

object NotificationGenerator {
    val NOTIFY_PREVIOUS = "com.example.androiddeezer.previous"
    val NOTIFY_DELETE = "com.example.androiddeezer.delete"
    val NOTIFY_PAUSE = "com.example.androiddeezer.pause"
    val NOTIFY_PLAY = "com.example.androiddeezer.play"
    val NOTIFY_NEXT = "com.example.androiddeezer.next"

    private val NOTIFICATION_ID_OPEN_ACTIVITY = 9
    private val NOTIFICATION_ID_CUSTOM_BIG = 9
    /*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel1 = new NotificationChannel(
                        CHANNEL_ID_1,
                        CHANNEL_NAME_1,
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel1.enableLights(true);
                channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
     //           NotificationCompat.Builder nc = new NotificationCompat.Builder(context);
     //           NotificationManager nm = (NotificationManager) context.getSystemService(NotificationManager.class);*/

    fun customBigNotification(context: Context) {
        val expandedView = RemoteViews(context.packageName, R.layout.big_notification)

        val nc = NotificationCompat.Builder(context)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyIntent = Intent(context, MainActivity::class.java)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        nc.setContentIntent(pendingIntent)
        nc.setSmallIcon(R.drawable.ic_action_play)
        nc.setAutoCancel(true)
        nc.setCustomBigContentView(expandedView)
        nc.setContentTitle("Music Player")
        nc.setContentText("Control Audio")
        //nc.().setContentText(R.id.textSongName, "Adele");
        //   nc.getBigContentView().setTextViewText(R.id.textSongName, "Adele");

        setListeners(expandedView, context)

        nm.notify(NOTIFICATION_ID_CUSTOM_BIG, nc.build())
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
