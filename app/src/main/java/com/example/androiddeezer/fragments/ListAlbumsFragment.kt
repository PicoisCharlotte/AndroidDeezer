package com.example.androiddeezer.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androiddeezer.MainActivity

import com.example.androiddeezer.R
import com.example.androiddeezer.adapters.AlbumAdapter
import com.example.androiddeezer.adapters.TrackAdapter
import com.example.androiddeezer.models.Album
import interfaces.AdapterCallbackAlbum
import kotlinx.android.synthetic.main.fragment_list_albums.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ListAlbumsFragment : Fragment(), AdapterCallbackAlbum {
    private val client = OkHttpClient()

    var albumList: MutableList<Album> = mutableListOf()
    val url = "http://api.deezer.com/2.0/user/2529/albums"
    lateinit var albumAdapter: AlbumAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        albumAdapter = AlbumAdapter(context, this)
        linearLayoutManager = LinearLayoutManager(context)

        return inflater.inflate(R.layout.fragment_list_albums, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_album_view.layoutManager = GridLayoutManager(context, 1) as RecyclerView.LayoutManager?
        list_album_view.adapter = albumAdapter

        getAlbums(url)

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

                    activity.runOnUiThread {
                        albumAdapter.setData(albumList)
                        albumAdapter.notifyDataSetChanged()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })
    }

    override fun onClickItem(album: Album) {
        val fragment = ListTracksFragment.newInstance()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.remove(this)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun goToTracks(idAlbum: Int?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
