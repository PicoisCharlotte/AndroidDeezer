package com.example.androiddeezer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.androiddeezer.managers.MusicManager
import com.example.androiddeezer.services.MusicService
import kotlinx.android.synthetic.main.activity_music.*

class NotificationBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

//        Toast.makeText(context, "Broadcast Received", Toast.LENGTH_LONG).show()

        if (intent.action == NotificationGenerator.NOTIFY_PLAY) {
            context.stopService(Intent(context, MusicService::class.java))
            val intent = Intent(context, MusicService::class.java)
            intent.putExtra("preview", MusicManager.newInstance(context).getCurrentTrack().getPreview())
            context.startService(intent)
            MusicManager.newInstance(context).setActive(true)
            Toast.makeText(context, "NOTIFY_PLAY", Toast.LENGTH_LONG).show()

        } else if (intent.action == NotificationGenerator.NOTIFY_PAUSE) {
            val mainActivity = context as MainActivity
            mainActivity.stopService(Intent(mainActivity, MusicService::class.java))
            Toast.makeText(context, "NOTIFY_PAUSE", Toast.LENGTH_LONG).show()

        } else if (intent.action == NotificationGenerator.NOTIFY_NEXT) {
            MusicService.newInstance().nextMusic(MusicManager.newInstance(context).getPosition(),
                MusicManager.newInstance(context).getCurrentTrackList(), context)
            context.stopService(Intent(context, MusicService::class.java))
            val intent = Intent(context, MusicService::class.java)
            intent.putExtra("preview", MusicManager.newInstance(context).getCurrentTrack().getPreview())
            context.startService(intent)

        } else if (intent.action == NotificationGenerator.NOTIFY_DELETE) {
            Toast.makeText(context, "NOTIFY_DELETE", Toast.LENGTH_LONG).show()
            NotificationGenerator.launchNotif(context, "EN haut ",  R.drawable.album_art)

        } else if (intent.action == NotificationGenerator.NOTIFY_PREVIOUS) {
            MusicService.newInstance().previousMusic(MusicManager.newInstance(context).getPosition(),
                MusicManager.newInstance(context).getCurrentTrackList(), context)
            context.stopService(Intent(context, MusicService::class.java))
            val intent = Intent(context, MusicService::class.java)
            intent.putExtra("preview", MusicManager.newInstance(context).getCurrentTrack().getPreview())
            context.startService(intent)
            Toast.makeText(context, "NOTIFY_PREVIOUS", Toast.LENGTH_LONG).show()
        }
    }
}
