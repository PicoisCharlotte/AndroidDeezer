package com.example.androiddeezer.fragments

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.androiddeezer.R
import com.example.androiddeezer.adapters.AlbumAdapter
import com.example.androiddeezer.adapters.TrackAdapter
import com.example.androiddeezer.models.Album
import com.example.androiddeezer.models.Tracks
import com.squareup.picasso.Picasso
import interfaces.AdapaterCallbackTracksNotFound
import kotlinx.android.synthetic.main.fragment_list_tracks.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ListTracksFragment :Fragment(), AdapaterCallbackTracksNotFound {
    private val client = OkHttpClient()

    var trackList: MutableList<Tracks> = mutableListOf()
    //Adapt with correct id
    var url = "http://api.deezer.com/2.0/album/$albumId/tracks"

    lateinit var trackAdapter: TrackAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        trackAdapter = TrackAdapter(context)
        linearLayoutManager = LinearLayoutManager(context)


        return inflater.inflate(R.layout.fragment_list_tracks, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_track_view.layoutManager = GridLayoutManager(context, 1)
        list_track_view.adapter = trackAdapter

        trackAdapter.getAlbumImg(albumCoverBig!!).toString()

        Picasso.get().load(albumCoverBig).into(album_img)

        getTracks(url)
    }

    companion object {
        var albumId: Int? = null
        var albumCoverBig: String? = null
        fun newInstance(album: Album): ListTracksFragment {
            albumId = album.id
            albumCoverBig = album.cover_big

            return ListTracksFragment()
        }
    }
    fun getTracks(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e : IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                try {
                    val json = JSONObject(responseData)
                    val datas = json.getJSONArray("data")

                    println("Request successful!")
                    for(i in 0..(datas.length() - 1)) {
                        val track = datas.getJSONObject(i)

                        trackList.add(Tracks(track))
                    }

                    for (i in 0..(trackList.size - 1)) {
                        println("track ${trackList[i]}")
                    }

                    activity.runOnUiThread {
                        trackAdapter.setData(trackList)

                        trackAdapter.notifyDataSetChanged()
                    }

                } catch (e: JSONException) {
                    activity.runOnUiThread {
                        context.toast("No datas found for this tracklist")
                    }
                    onClickItem()
                    e.printStackTrace()
                }

            }
        })
    }

    fun Context.toast(message: CharSequence) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onClickItem() {
        println("No datas found")
        val fragment = NoTracksFoundFragment.newInstance()
        if(!fragment.isAdded) {
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.content, fragment)
            transaction.remove(this)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return
    }

}
