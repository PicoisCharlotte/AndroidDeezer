package com.example.androiddeezer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.androiddeezer.fragments.ListAlbumsFragment

class MainActivity : AppCompatActivity() {


    private var mediaPlayer = MediaPlayer()
    public var isActive = false
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
