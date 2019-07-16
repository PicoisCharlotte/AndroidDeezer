package com.example.androiddeezer

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.androiddeezer.activities.MusicActivity
import com.example.androiddeezer.managers.MusicManager
import com.example.androiddeezer.models.Track
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.music_controller.*
import kotlinx.android.synthetic.main.music_controller.music_controller

class MainActivity : AppCompatActivity() {

    private var mediaPlayer = MediaPlayer()
    public var isActive = false
    public lateinit var currentTrack: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setMusicControllerVisibility(isActive)
        go_to_music.setOnClickListener({
             intent = Intent(this, MusicActivity::class.java)
            startActivity(intent);
        })
    }

    fun activateMusic(){
        setOnclicks()
    }

    override fun onResume() {
        super.onResume()

        setMusicControllerVisibility(isActive)
    }

    fun setMusicControllerVisibility(visible: Boolean){
        if(visible)
            music_controller.visibility = VISIBLE
        else
            music_controller.visibility = GONE
    }

    fun setOnclicks(){
        if(currentTrack != null) {
            btn_back.setOnClickListener({
                MusicManager.newInstance(this).previous()
            })
            btn_play.setOnClickListener({
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                MusicManager.newInstance(this).play()
            })
            btn_pause.setOnClickListener({
                btn_play.visibility = View.VISIBLE
                btn_pause.visibility = View.GONE
                MusicManager.newInstance(this).pause()
            })
            btn_next.setOnClickListener({
                MusicManager.newInstance(this).next()
            })
        }
    }
}
