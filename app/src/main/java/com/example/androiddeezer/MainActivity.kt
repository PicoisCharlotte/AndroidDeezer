package com.example.androiddeezer

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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
import com.example.androiddeezer.fragments.ListAlbumsFragment
import com.example.androiddeezer.services.MusicService

class MainActivity : AppCompatActivity() {


    private var mediaPlayer = MediaPlayer()
    public lateinit var currentTrack: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val albumFragment = ListAlbumsFragment.newInstance()
        openFragment(albumFragment)
    }

    private fun openFragment(fragment: ListAlbumsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }
    fun activateMusic(){
        setOnClicks()
    }

    override fun onResume() {
        super.onResume()

        setMusicControllerVisibility(MusicManager.newInstance(this@MainActivity).isActive())
    }

    fun setMusicControllerVisibility(visible: Boolean){
        if(music_controller != null){
            if (visible)
                music_controller.visibility = VISIBLE
            else
                music_controller.visibility = GONE
        }
    }

    fun setOnClicks(){
            btn_back.setOnClickListener({
            })
            btn_play.setOnClickListener({
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                startService(Intent(this, MusicService::class.java))
            })
            btn_pause.setOnClickListener({
                btn_play.visibility = View.VISIBLE
                btn_pause.visibility = View.GONE
                stopService(Intent(this, MusicService::class.java))
            })
            btn_next.setOnClickListener({
            })
    }


}
