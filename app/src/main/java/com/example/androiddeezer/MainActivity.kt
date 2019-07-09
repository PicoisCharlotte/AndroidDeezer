package com.example.androiddeezer

import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.androiddeezer.models.Track
import kotlinx.android.synthetic.main.music_controller.*

class MainActivity : AppCompatActivity() {


    private var mediaPlayer = MediaPlayer()
    public var isActive = false
    public lateinit var currentTrack: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setMusicControllerVisibility(isActive)
    }

    fun activateMusic(track: Track){
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
                Toast.makeText(this@MainActivity, "BACK", Toast.LENGTH_LONG)


            })
            btn_play.setOnClickListener({
                Toast.makeText(this@MainActivity, "PLAY", Toast.LENGTH_LONG)
                btn_pause.visibility = View.VISIBLE
                btn_play.visibility = View.GONE
                try {
                    val previewUri: Uri = Uri.parse(currentTrack.getPreview())
                    mediaPlayer.setDataSource(this@MainActivity, previewUri)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: Exception) {
                    Toast.makeText(this, "The file does not exist", Toast.LENGTH_LONG).show()
                }
            })
            btn_pause.setOnClickListener({
                Toast.makeText(this@MainActivity, "PAUSE", Toast.LENGTH_LONG)
                btn_play.visibility = View.VISIBLE
                btn_pause.visibility = View.GONE
                mediaPlayer.pause()
            })
            btn_next.setOnClickListener({
                Toast.makeText(this@MainActivity, "NEXT", Toast.LENGTH_LONG)


            })
        }
    }
}
