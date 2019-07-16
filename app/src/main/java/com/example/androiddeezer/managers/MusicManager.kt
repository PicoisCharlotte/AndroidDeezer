package com.example.androiddeezer.managers

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import com.example.androiddeezer.models.Track

class MusicManager(context: Context) {

    private var mediaPlayer = MediaPlayer()
    private var context:Context = context
    private var settings: SharedPreferences? = context.getSharedPreferences("DeezerApp", 0)


    public lateinit var currentTrack: Track
    public lateinit var currentListTrack: List<Track>


    companion object {
        fun newInstance(contextInstance: Context): MusicManager {
            return MusicManager(contextInstance)
        }
    }

    public fun play(){
        try {
            val previewUri: Uri = Uri.parse(currentTrack.getPreview())
            mediaPlayer.setDataSource(context, previewUri)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            Toast.makeText(context, "The file does not exist", Toast.LENGTH_LONG).show()
        }
    }
    public fun stop(){

    }
    public fun pause(){
        mediaPlayer.pause()
    }
    public fun next(position: Int){

    }
    public fun previous(position: Int){

    }
}