package com.example.androiddeezer.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androiddeezer.R
import com.example.androiddeezer.adapters.AlbumAdapter
import com.example.androiddeezer.adapters.TrackAdapter
import com.example.androiddeezer.models.Tracks
import kotlinx.android.synthetic.main.fragment_list_tracks.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ListTracksFragment :Fragment() {
    private val client = OkHttpClient()
    var trackList: MutableList<Tracks> = mutableListOf()
    //Adapt with correct id
    var url = "http://api.deezer.com/2.0/album/49201/tracks"
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

        list_track_view.layoutManager = GridLayoutManager(context, 1) as RecyclerView.LayoutManager?
        list_track_view.adapter = trackAdapter


        getTracks(url)
    }

    companion object {
        fun newInstance(): ListTracksFragment {
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

                    println("Request successful!")
                    println(json)

                    val datas = json.getJSONArray("data")
                    for(i in 0..(datas.length() - 1)) {
                        val track = datas.getJSONObject(i)

                        trackList.add(Tracks(track))
                    }

                    for (i in 0..(trackList.size - 1)) {
                        println("track ${trackList[i]}")
                    }

                    activity.runOnUiThread(java.lang.Runnable {
                        trackAdapter.setData(trackList)
                        trackAdapter.notifyDataSetChanged()
                    })

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })
    }

}