package com.example.androiddeezer.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androiddeezer.R
import com.example.androiddeezer.adapters.AlbumAdapter
import com.example.androiddeezer.models.Album
import com.example.androiddeezer.interfaces.AdapterCallbackAlbum
import com.example.androiddeezer.managers.MusicManager
import kotlinx.android.synthetic.main.fragment_list_albums.*
import kotlinx.android.synthetic.main.music_controller.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.music_controller.music_controller


class ListAlbumsFragment : Fragment(), AdapterCallbackAlbum {
    private val client = OkHttpClient()

    val url = "http://api.deezer.com/2.0/user/2529/albums"
    var albumList: MutableList<Album> = mutableListOf()

    lateinit var albumAdapter: AlbumAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        albumAdapter = AlbumAdapter(context!!, this)
        linearLayoutManager = LinearLayoutManager(context)

        return inflater.inflate(R.layout.fragment_list_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_album_view.layoutManager = GridLayoutManager(context, 1) as RecyclerView.LayoutManager?
        list_album_view.adapter = albumAdapter

        getAlbums(url)
    }

    override fun onResume() {
        super.onResume()
        setMusicControllerVisibility(MusicManager.newInstance(requireContext()).isActive())
    }

    fun setMusicControllerVisibility(visible: Boolean){
        if (visible) {
            if(music_controller != null) music_controller.visibility = View.VISIBLE
        } else
            if(music_controller != null) music_controller.visibility = View.GONE
    }

    companion object {

        fun newInstance(): ListAlbumsFragment {
            return ListAlbumsFragment()
        }
    }
    fun getAlbums(url: String) {

        albumList.clear()

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

                    activity?.runOnUiThread {
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
        val fragment = ListTracksFragment.newInstance(album)
        if(fragment.isAdded) {
            return
        } else {
            val transaction = fragmentManager?.beginTransaction()

            transaction?.replace(R.id.content, fragment)
            //transaction.remove(this)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }
}
