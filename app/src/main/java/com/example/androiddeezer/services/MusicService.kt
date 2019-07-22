package com.example.androiddeezer.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.widget.Toast
import com.example.androiddeezer.managers.MusicManager
import com.example.androiddeezer.models.Track


class MusicService : Service() {
    var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance(): MusicService {
            return MusicService()
        }
    }

    override fun onCreate() {
        Toast.makeText(this, "Service Successfully Created", Toast.LENGTH_LONG).show()

        mediaPlayer = MediaPlayer()
    }

    override fun onStart(intent: Intent, startid: Int) {
        val preview = intent.getStringExtra("preview")
        val previewUri: Uri = Uri.parse(preview)
        mediaPlayer?.setDataSource(this, previewUri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service Stopped and Music Stopped", Toast.LENGTH_LONG).show()
        mediaPlayer?.stop()
    }

    fun nextMusic(position: Int, listTracks: List<Track>, context: Context){
        if(position + 1 < listTracks.size) {
            MusicManager.newInstance(context).setCurrentTrack(listTracks.get(position + 1))
            MusicManager.newInstance(context).setPosition(position + 1)
        } else {
            MusicManager.newInstance(context).setCurrentTrack(listTracks.get(0))
            MusicManager.newInstance(context).setPosition(0)
        }
    }

    fun previousMusic(position: Int, listTracks: List<Track>, context: Context){
        if(position == 0){
            MusicManager.newInstance(context).setCurrentTrack(listTracks.get(listTracks.size - 1))
            MusicManager.newInstance(context).setPosition(listTracks.size - 1)
        } else {
            MusicManager.newInstance(context).setCurrentTrack(listTracks.get(position - 1))
            MusicManager.newInstance(context).setPosition(position - 1)
        }
    }
}