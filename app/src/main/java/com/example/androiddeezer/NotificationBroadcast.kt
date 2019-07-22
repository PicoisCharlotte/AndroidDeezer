package com.example.androiddeezer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.androiddeezer.managers.MusicManager
import com.example.androiddeezer.services.MusicService

class NotificationBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == NotificationGenerator.NOTIFY_PLAY) {
            context.stopService(Intent(context, MusicService::class.java))
            val intent = Intent(context, MusicService::class.java)
            intent.putExtra("preview", MusicManager.newInstance(context).getCurrentTrack().getPreview())
            context.startService(intent)
            MusicManager.newInstance(context).setActive(true)

        } else if (intent.action == NotificationGenerator.NOTIFY_PAUSE) {
            context.stopService(Intent(context, MusicService::class.java))

        } else if (intent.action == NotificationGenerator.NOTIFY_NEXT) {
            MusicService.newInstance().nextMusic(MusicManager.newInstance(context).getPosition(),
                MusicManager.newInstance(context).getCurrentTrackList(), context)
            context.stopService(Intent(context, MusicService::class.java))
            val intent = Intent(context, MusicService::class.java)
            intent.putExtra("preview", MusicManager.newInstance(context).getCurrentTrack().getPreview())
            context.startService(intent)

        } else if (intent.action == NotificationGenerator.NOTIFY_PREVIOUS) {
            MusicService.newInstance().previousMusic(MusicManager.newInstance(context).getPosition(),
                MusicManager.newInstance(context).getCurrentTrackList(), context)
            context.stopService(Intent(context, MusicService::class.java))
            val intent = Intent(context, MusicService::class.java)
            intent.putExtra("preview", MusicManager.newInstance(context).getCurrentTrack().getPreview())
            context.startService(intent)
        }
    }
}
