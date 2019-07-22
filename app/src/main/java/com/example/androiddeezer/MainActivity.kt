package com.example.androiddeezer

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.androiddeezer.managers.MusicManager
import com.example.androiddeezer.models.Track
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import com.example.androiddeezer.fragments.ListAlbumsFragment
import com.example.androiddeezer.services.MusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var notificationManager: NotificationManager? = null


    private var mediaPlayer = MediaPlayer()
    public lateinit var currentTrack: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val albumFragment = ListAlbumsFragment.newInstance()
        openFragment(albumFragment)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager =
                getSystemService(
                    Context.NOTIFICATION_SERVICE) as NotificationManager

            createNotificationChannel(
                "com.example.androiddezer.player",
                "Player",
                "Player Android Deezer")
         }

        setOnClicks()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String,
                                          description: String) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }

    private fun openFragment(fragment: ListAlbumsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
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
        if(MusicManager.newInstance(this@MainActivity).getCurrentTrack() != null) {
            btn_back.setOnClickListener({
                MusicService.newInstance().previousMusic(MusicManager.newInstance(this@MainActivity).getPosition(),
                MusicManager.newInstance(this@MainActivity).getCurrentTrackList(), this@MainActivity)
                stopService(Intent(this, MusicService::class.java))
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                val intent = Intent(this@MainActivity, MusicService::class.java)
                intent.putExtra("preview", MusicManager.newInstance(this@MainActivity).getCurrentTrack().getPreview())
                startService(intent)
            })
            btn_play.setOnClickListener({
                stopService(Intent(this, MusicService::class.java))
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                val intent = Intent(this@MainActivity, MusicService::class.java)
                intent.putExtra("preview", MusicManager.newInstance(this@MainActivity).getCurrentTrack().getPreview())
                startService(intent)
                MusicManager.newInstance(this@MainActivity).setActive(true)
            })
            btn_pause.setOnClickListener({
                btn_play.visibility = View.VISIBLE
                btn_pause.visibility = View.GONE
                stopService(Intent(this, MusicService::class.java))
            })
            btn_next.setOnClickListener({
                MusicService.newInstance().nextMusic(MusicManager.newInstance(this@MainActivity).getPosition(),
                MusicManager.newInstance(this@MainActivity).getCurrentTrackList(), this@MainActivity)
                stopService(Intent(this, MusicService::class.java))
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                val intent = Intent(this@MainActivity, MusicService::class.java)
                intent.putExtra("preview", MusicManager.newInstance(this@MainActivity).getCurrentTrack().getPreview())
                startService(intent)
            })
        }
    }

    private fun onPlay(){
        //NotificationGenerator.openActivityNotification(applicationContext)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationGenerator.newNotif(notificationManager, applicationContext, "Titre",  R.drawable.ic_action_pause)
        } else
            NotificationGenerator.customBigNotification(applicationContext, "Titre",  R.drawable.ic_action_pause)
    }




}


