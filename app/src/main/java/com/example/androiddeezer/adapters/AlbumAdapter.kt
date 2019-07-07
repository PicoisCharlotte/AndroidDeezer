package com.example.androiddeezer.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androiddeezer.R
import com.example.androiddeezer.models.Album
import com.example.androiddeezer.models.Artist
import kotlinx.android.synthetic.main.item_album.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class AlbumAdapter(val context: Context): Adapter<ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var albumList: MutableList<Album> = ArrayList()
    private var artist: Artist? = null

    fun setData(data: MutableList<Album>) {
        albumList = data

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView: View
        rowView = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)
        return ViewHolder(rowView)

    }


    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = albumList[position]

        holder.itemView.album_title.text = album.title
        holder.itemView.album_artist.text = album.artist?.name
        holder.itemView.album_release_date.text = album.release_date

    }
}

