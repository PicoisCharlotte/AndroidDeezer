package com.example.androiddeezer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == NotificationGenerator.NOTIFY_PLAY) {
            Toast.makeText(context, "NOTIFY_PLAY", Toast.LENGTH_LONG).show()
        } else if (intent.action == NotificationGenerator.NOTIFY_PAUSE) {
            Toast.makeText(context, "NOTIFY_PAUSE", Toast.LENGTH_LONG).show()
        } else if (intent.action == NotificationGenerator.NOTIFY_NEXT) {
            Toast.makeText(context, "NOTIFY_NEXT", Toast.LENGTH_LONG).show()
            NotificationGenerator.customBigNotification(context, "EN bas ",  R.drawable.ic_action_fast_backward)
        } else if (intent.action == NotificationGenerator.NOTIFY_DELETE) {
            Toast.makeText(context, "NOTIFY_DELETE", Toast.LENGTH_LONG).show()
            NotificationGenerator.customBigNotification(context, "EN haut ",  R.drawable.album_art)
        } else if (intent.action == NotificationGenerator.NOTIFY_PREVIOUS) {
            Toast.makeText(context, "NOTIFY_PREVIOUS", Toast.LENGTH_LONG).show()
        }
    }
}
