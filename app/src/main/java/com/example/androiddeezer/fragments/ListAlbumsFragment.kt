package com.example.androiddeezer.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.androiddeezer.R
import com.example.androiddeezer.adapters.AlbumAdapter
import com.example.androiddeezer.models.Album
import kotlinx.android.synthetic.main.fragment_list_albums.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ListAlbumsFragment : Fragment() {
    private val client = OkHttpClient()
    var albumList: MutableList<Album> = mutableListOf()
    val url = "http://api.deezer.com/2.0/user/2529/albums"
    lateinit var albumAdapter: AlbumAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        albumAdapter = AlbumAdapter(context)
        linearLayoutManager = LinearLayoutManager(context)
        return inflater.inflate(R.layout.fragment_list_albums, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_album_view.setLayoutManager(GridLayoutManager(context, 1))
        list_album_view.adapter = albumAdapter
        getAlbums(url)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        fun newInstance(): ListAlbumsFragment {
            return ListAlbumsFragment()
        }
    }

    fun getAlbums(url: String) {
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
                        val album = datas.getJSONObject(i)

                        albumList.add(Album(album))
                    }

                    for (i in 0..(albumList.size - 1)) {
                        println("album ${albumList[i]}")
                    }

                    activity.runOnUiThread(java.lang.Runnable{
                        albumAdapter.setData(albumList)
                        albumAdapter.notifyDataSetChanged()
                    })

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })
    }
}