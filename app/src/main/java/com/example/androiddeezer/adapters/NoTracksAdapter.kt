package com.example.androiddeezer.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androiddeezer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_no_tracks.view.*

class NoTracksAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rowView: View
        rowView = LayoutInflater.from(context).inflate(R.layout.item_no_tracks, parent, false)
        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder?.itemView?.no_tracks_title?.text  = "No tracks here"

        Picasso.get().load(R.drawable.ic_launcher_background).into(holder?.itemView?.no_track_image)
    }
}