package com.example.androiddeezer.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import android.R.attr.start
import android.R
import android.media.MediaPlayer
import android.net.Uri
import com.example.androiddeezer.managers.MusicManager


class MusicService : Service() {
    var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        Toast.makeText(this, "Service Successfully Created", Toast.LENGTH_LONG).show()

        mediaPlayer = MediaPlayer()
        //setting loop play to true
        //this will make the ringtone continuously playing        myPlayer.setLooping(false); // Set looping
    }

    override fun onStart(intent: Intent, startid: Int) {
        val previewUri: Uri = Uri.parse(MusicManager.newInstance(this@MusicService).getCurrentTrack().getPreview())
        mediaPlayer?.setDataSource(this@MusicService, previewUri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service Stopped and Music Stopped", Toast.LENGTH_LONG).show()
        mediaPlayer?.stop()
    }


}