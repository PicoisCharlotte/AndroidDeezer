package com.example.androiddeezer.fragments

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.graphics.Palette
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.androiddeezer.R
import com.example.androiddeezer.activities.MusicActivity
import com.example.androiddeezer.adapters.TrackAdapter
import com.example.androiddeezer.interfaces.AdapterCallbackTrack
import com.example.androiddeezer.managers.MusicManager
import com.example.androiddeezer.models.Album
import com.example.androiddeezer.models.Track
import com.example.androiddeezer.models.Tracks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_list_tracks.*
import kotlinx.android.synthetic.main.music_controller.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class ListTracksFragment : Fragment(), AdapterCallbackTrack{
    private val client = OkHttpClient()

    var trackList: MutableList<Track> = mutableListOf()

    var url = "http://api.deezer.com/2.0/album/$albumId/tracks"

    lateinit var trackAdapter: TrackAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        trackAdapter = TrackAdapter(context!!, this)
        linearLayoutManager = LinearLayoutManager(context)

        return inflater.inflate(R.layout.fragment_list_tracks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_track_view.layoutManager = GridLayoutManager(context, 1)
        list_track_view.adapter = trackAdapter

        trackAdapter.getAlbumImg(albumCoverBig!!).toString()

        Picasso.get().load(albumCoverBig).into(album_img)

        createGradientFromAlbumCover()

        getTracks(url)

        setMusicControllerVisibility(MusicManager.newInstance(requireContext()).isActive())
    }

    fun setMusicControllerVisibility(visible: Boolean){
        if(music_controller != null) {
            if (visible) {
                music_controller.visibility = View.VISIBLE


            } else
                music_controller.visibility = View.GONE
        }
    }

    companion object {
        var albumId: Int? = null
        var albumCoverBig: String? = null
        fun newInstance(album: Album): ListTracksFragment {
            albumId = album.getId()
            albumCoverBig = album.getCoverBig()

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
                    if(json.has("data")) {
                        val datas = json.getJSONArray("data")

                        for(i in 0..(datas.length() - 1)) {
                            val track = datas.getJSONObject(i)

                            trackList.add(Track(track))
                        }

                        activity?.runOnUiThread {
                            trackAdapter.setData(trackList)
                            trackAdapter.notifyDataSetChanged()
                        }
                    } else {
                        activity?.runOnUiThread {
                            list_empty.visibility = VISIBLE
                        }
                    }
                } catch (e: JSONException) {

                }
            }
        })
    }

    fun createGradientFromAlbumCover() {
        Picasso
            .get()
            .load(albumCoverBig)
            .into(album_img, object: com.squareup.picasso.Callback {
                override fun onError(e: java.lang.Exception?) {
                }

                override fun onSuccess() {
                    val bitmap: BitmapDrawable = album_img.drawable as BitmapDrawable
                    val palette = Palette.from(bitmap.bitmap).generate()

                    val profileColor = palette.swatches

                    val profileInt = mutableListOf<Int>()

                    for(color: Palette.Swatch in profileColor) {
                        profileInt.add(color.rgb)
                    }

                    val gd = GradientDrawable(
                        GradientDrawable.Orientation.BOTTOM_TOP,
                        profileInt.toIntArray()
                    )

                    image_background.background = gd

                }
            })
    }

    override fun onClickItem(track: Track, trackList: List<Track>, position: Int) {
        context?.let { MusicManager.newInstance(it).setCurrentTrack(track) }
        val arrayListTrack = ArrayList<Track>(trackList)
        MusicManager.newInstance(requireContext()).setPosition(position)
        MusicManager.newInstance(requireContext()).setCurrentTrackList(trackList)
        var intent = Intent(context, MusicActivity::class.java)
        startActivity(intent)
    }
}
