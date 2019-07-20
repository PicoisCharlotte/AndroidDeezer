package com.example.androiddeezer.managers

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import com.example.androiddeezer.models.Track
import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.HashMap

class MusicManager(context: Context) {

    val gson = Gson()
    private var context:Context = context
    private var settings: SharedPreferences? = context.getSharedPreferences("DeezerApp", 0)

    companion object {
        fun newInstance(contextInstance: Context): MusicManager {
            return MusicManager(contextInstance)
        }
    }

    public fun isActive(): Boolean{
        return settings!!.getBoolean("isActive", false)
    }
    public fun setActive(isActive: Boolean){
        settings?.edit()?.putBoolean("isActive", false)
    }

    public fun getCurrentTrack(): Track{
        val jsonTrack = settings?.getString("track", "")
        val track = gson.fromJson(jsonTrack, Track::class.java)
        return track
    }
    public fun setCurrentTrack(track: Track){
        val trackJson = gson.toJson(track)
        settings?.edit()?.putString("track", trackJson)?.apply()
    }

    public fun getCurrentTrackList(): List<Track>{
        val jsonTrackList = settings?.getString("currentTrackList", "")
        val trackType = object : TypeToken<List<Track>>() {}.type
        val trackList = Gson().fromJson<List<Track>>(jsonTrackList, trackType)
        return trackList
    }
    public fun setCurrentTrackList(trackList: List<Track>){
        val trackListJson = gson.toJson(trackList)
        settings?.edit()?.putString("currentTrackList", trackListJson)?.apply()
    }

    public fun getPosition(): Int{
        return settings!!.getInt("position", 0)
    }
    public fun setPosition(position: Int){
        settings?.edit()?.putInt("position", position)?.apply()
    }
}