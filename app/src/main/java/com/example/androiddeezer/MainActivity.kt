package com.example.androiddeezer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.androiddeezer.fragments.ListAlbumsFragment
import com.example.androiddeezer.managers.MusicManager
import com.example.androiddeezer.models.Track
import com.example.androiddeezer.services.MusicService
import kotlinx.android.synthetic.main.activity_music.*

class MainActivity : AppCompatActivity() {
    private var notificationManager: NotificationManager? = null

    lateinit var notificationBroadcast: NotificationBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MusicManager.newInstance(this).setActive(false)



        val albumFragment = ListAlbumsFragment.newInstance()
        openFragment(albumFragment)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager =
                getSystemService(
                    Context.NOTIFICATION_SERVICE) as NotificationManager

            NotificationGenerator.createNotificationChannel("com.example.androiddezer.player",
                "Player",
                "Player Android Deezer")

            NotificationGenerator.launchNotifManager(applicationContext)

            notificationBroadcast = NotificationBroadcast()

            registerReceiver(notificationBroadcast, IntentFilter("com.example.androiddeezer.play"))
            registerReceiver(notificationBroadcast, IntentFilter("com.example.androiddeezer.pause"))
            registerReceiver(notificationBroadcast, IntentFilter("com.example.androiddeezer.next"))
            registerReceiver(notificationBroadcast, IntentFilter("com.example.androiddeezer.previous"))
        }

        setOnClicks()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MusicService::class.java))

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        unregisterReceiver(notificationBroadcast)
        notificationManager.cancel(101)
    }

    private fun openFragment(fragment: ListAlbumsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
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
            btn_back.setOnClickListener {
                MusicService.newInstance().previousMusic(MusicManager.newInstance(this@MainActivity).getPosition(),
                    MusicManager.newInstance(this@MainActivity).getCurrentTrackList(), this@MainActivity)
                stopService(Intent(this, MusicService::class.java))
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                val intent = Intent(this@MainActivity, MusicService::class.java)

                intent.putExtra("preview", MusicManager.newInstance(this@MainActivity).getCurrentTrack().getPreview())
                startService(intent)
            }
            btn_play.setOnClickListener {
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                stopService(Intent(this, MusicService::class.java))
                val intent = Intent(this@MainActivity, MusicService::class.java)
                intent.putExtra("preview", MusicManager.newInstance(this@MainActivity).getCurrentTrack().getPreview())
                startService(intent)

                MusicManager.newInstance(this@MainActivity).setActive(true)
            }
            btn_pause.setOnClickListener {
                btn_play.visibility = View.VISIBLE
                btn_pause.visibility = View.GONE
                stopService(Intent(this, MusicService::class.java))
            }
            btn_next.setOnClickListener {
                MusicService.newInstance().nextMusic(MusicManager.newInstance(this@MainActivity).getPosition(),
                    MusicManager.newInstance(this@MainActivity).getCurrentTrackList(), this@MainActivity)
                stopService(Intent(this, MusicService::class.java))
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                val intent = Intent(this@MainActivity, MusicService::class.java)
                intent.putExtra("preview", MusicManager.newInstance(this@MainActivity).getCurrentTrack().getPreview())
                startService(intent)
            }
        }
    }





}


