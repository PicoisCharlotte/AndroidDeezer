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
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import android.content.Intent
import android.widget.Button
import com.bumptech.glide.GenericTransitionOptions
import com.example.androiddeezer.managers.MusicManager
import com.example.androiddeezer.services.MusicService


class MusicActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private var track = Track()
    private var album = Album()
    private var artist = Artist()
    private val mainAct = MainActivity()
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        position = MusicManager.newInstance(this@MusicActivity).getPosition()

        setOnclicks()
        //mainAct.setMusicControllerVisibility(false)
        getMusic()

        val button = findViewById<Button>(R.id.playFab)
        button.setOnClickListener {onPlay()}
    }

    fun getMusic(){
        var urlString = "https://api.deezer.com/track/"
        urlString += MusicManager.newInstance(this).getCurrentTrack().getIdTrack()
        val url = URL(urlString)
        val request = Request.Builder()
            .url(url)
            .build()

        val callApi = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body?.string()

                try {
                    val Jobject = JSONObject(body)

                    if(Jobject.has("error")){

                    } else {
                        track = Track(Jobject)
                        album = track.getAlbum()
                        artist = track.getArtist()
                        this@MusicActivity.runOnUiThread(java.lang.Runnable {
                            Glide.with(this@MusicActivity)
                                .load(album.getCoverMedium())
                                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                                .into(image_album)
                        })
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })

    }
    private fun onPlay(){
        NotificationGenerator.launchNotif(applicationContext, "Titre",  R.drawable.ic_action_pause)

    }
    fun setOnclicks(){
        if(MusicManager.newInstance(this@MusicActivity).getCurrentTrack() != null) {
            btn_back.setOnClickListener({
                MusicService.newInstance().previousMusic(MusicManager.newInstance(this@MusicActivity).getPosition(), MusicManager.newInstance(this@MusicActivity).getCurrentTrackList(), this@MusicActivity)
                stopService(Intent(this, MusicService::class.java))
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                val intent = Intent(this@MusicActivity, MusicService::class.java)
                intent.putExtra("preview", MusicManager.newInstance(this@MusicActivity).getCurrentTrack().getPreview())
                startService(intent)
            })
            btn_play.setOnClickListener({
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                val intent = Intent(this@MusicActivity, MusicService::class.java)
                intent.putExtra("preview", MusicManager.newInstance(this@MusicActivity).getCurrentTrack().getPreview())
                startService(intent)
                MusicManager.newInstance(this@MusicActivity).setActive(true)
            })
            btn_pause.setOnClickListener({
                btn_play.visibility = View.VISIBLE
                btn_pause.visibility = View.GONE
                stopService(Intent(this, MusicService::class.java))
            })
            btn_next.setOnClickListener({
                MusicService.newInstance().nextMusic(MusicManager.newInstance(this@MusicActivity).getPosition(), MusicManager.newInstance(this@MusicActivity).getCurrentTrackList(), this@MusicActivity)
                stopService(Intent(this, MusicService::class.java))
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                val intent = Intent(this@MusicActivity, MusicService::class.java)
                intent.putExtra("preview", MusicManager.newInstance(this@MusicActivity).getCurrentTrack().getPreview())
                startService(intent)
            })
        }
        close.setOnClickListener {
            finish()
        }
    }
}
