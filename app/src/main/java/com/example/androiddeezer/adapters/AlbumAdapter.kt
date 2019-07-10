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
import com.squareup.picasso.Picasso
import interfaces.AdapterCallbackAlbum
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumAdapter(val context: Context, private val adapterCallbackAlbum: AdapterCallbackAlbum): Adapter<ViewHolder>() {
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
        Picasso.get().load(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(holder.itemView.album_image)

        holder.itemView.album_image.alpha = 0f

        holder.itemView.album_image.animate().setDuration(1000).alpha(1f).start()
        Picasso.get().load(album.cover_medium).into(holder.itemView.album_image)

        holder.itemView.setOnClickListener { adapterCallbackAlbum.onClickItem(album)}
    }



}

