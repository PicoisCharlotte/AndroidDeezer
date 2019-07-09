package com.example.androiddeezer.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androiddeezer.R
import com.example.androiddeezer.models.Artist
import com.example.androiddeezer.models.Tracks
import kotlinx.android.synthetic.main.item_track.view.*

class TrackAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var trackList: MutableList<Tracks> = ArrayList()

    private var artist: Artist? = null
    fun setData(data: MutableList<Tracks>) {
        trackList = data

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView: View
        rowView = LayoutInflater.from(context).inflate(R.layout.item_track, parent, false)
        return ViewHolder(rowView)

    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val track = trackList[position]

        holder?.itemView?.track_title?.text = track.title
        holder?.itemView?.track_artist?.text = track.artist?.name
        holder?.itemView?.track_duration?.text = track.duration.toString()
    }


}
