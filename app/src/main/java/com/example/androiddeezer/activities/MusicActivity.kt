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

class MusicActivity : AppCompatActivity() {


    private val client = OkHttpClient()
    private var mediaPlayer = MediaPlayer()
    private var track = Track()
    private var album = Album()
    private var artist = Artist()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

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
                    album = track.getAlbum()
                    artist = track.getArtist()
                    this@MusicActivity.runOnUiThread(java.lang.Runnable {
                        Glide.with(this@MusicActivity)
                            .load(album.getCoverMedium())
                            .into(image_album);  // imageview object
                    })

                    setOnclicks()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })

    }

    fun setOnclicks(){
        btn_back.setOnClickListener({
            Toast.makeText(this@MusicActivity, "BACK", Toast.LENGTH_LONG)


        })
        btn_play.setOnClickListener({
            Toast.makeText(this@MusicActivity, "PLAY", Toast.LENGTH_LONG)
            btn_pause.visibility = VISIBLE
            btn_play.visibility = GONE
            try {
                val previewUri: Uri = Uri.parse(track.getPreview())
                mediaPlayer.setDataSource(this@MusicActivity, previewUri)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch(e: Exception){
                Toast.makeText(this, "The file does not exist", Toast.LENGTH_LONG).show()
            }
        })
        btn_pause.setOnClickListener({
            Toast.makeText(this@MusicActivity, "PAUSE", Toast.LENGTH_LONG)
            btn_play.visibility = VISIBLE
            btn_pause.visibility = GONE
            mediaPlayer.pause()
        })
        btn_next.setOnClickListener({
            Toast.makeText(this@MusicActivity, "NEXT", Toast.LENGTH_LONG)


        })
    }




}
