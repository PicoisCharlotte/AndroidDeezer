package com.example.androiddeezer.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androiddeezer.R
import com.example.androiddeezer.models.Tracks
import kotlinx.android.synthetic.main.item_track.view.*

class TrackAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var trackList: MutableList<Tracks> = ArrayList()
    private var albumCover: String? = null

    fun setData(data: MutableList<Tracks>) {
        trackList = data

        notifyDataSetChanged()
    }

    fun getAlbumImg(data: String) {
        albumCover = data

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder  {
        val rowView: View = LayoutInflater.from(context).inflate(R.layout.item_track, parent, false)

        return ViewHolder(rowView)

    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val track = trackList[position]

        holder?.itemView?.track_title?.text = track.title
        holder?.itemView?.track_artist?.text = track.artist?.name
        holder?.itemView?.track_duration?.text = convertSecondsToMinutesSeconds(track.duration!!)
    }

    fun convertSecondsToMinutesSeconds(secondToConvert: Int): String {
        val duration: Int = secondToConvert
        var seconds: Int? = duration % 60
        val minutes: Int? = (duration / 60) % 60


        return "$minutes:${String.format("%02d", seconds)}"
    }
}
