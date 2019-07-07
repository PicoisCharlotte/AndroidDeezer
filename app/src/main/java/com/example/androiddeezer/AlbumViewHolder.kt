package com.example.androiddeezer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.androiddeezer.models.Album

class AlbumViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_album, parent, false)){

    private var albumTitle: TextView? = null

    init {
        albumTitle = itemView.findViewById(R.id.album_title)
    }

    fun bind(album: Album) {
        albumTitle?.text = album.title
    }
}