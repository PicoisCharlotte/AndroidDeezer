package com.example.androiddeezer.activities

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.androiddeezer.MainActivity
import com.example.androiddeezer.R
import com.example.androiddeezer.models.Album
import com.example.androiddeezer.models.Artist
import com.example.androiddeezer.models.Track
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.android.synthetic.main.music_controller.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class MusicActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private var track = Track()
    private var album = Album()
    private var artist = Artist()
    private val mainAct = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        mainAct.setMusicControllerVisibility(false)
        getMusic()
    }

    fun getMusic(){
        var urlString = "https://api.deezer.com/track/3135556"
        val url = URL(urlString)
        val request = Request.Builder()
            .url(url)
            .build()

        val callApi = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response?.body()?.string()

                try {
                    val Jobject = JSONObject(body)

                    track = Track(Jobject)
                    mainAct.currentTrack = track
                    album = track.getAlbum()
                    artist = track.getArtist()
                    this@MusicActivity.runOnUiThread(java.lang.Runnable {
                        Glide.with(this@MusicActivity)
                            .load(album.getCoverMedium())
                            .into(image_album);  // imageview object
                    })
                    mainAct.isActive = true
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })

    }






}
