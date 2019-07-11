package com.example.androiddeezer.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androiddeezer.R
import com.example.androiddeezer.adapters.NoTracksAdapter
import kotlinx.android.synthetic.main.fragment_no_tracks.*

class NoTracksFoundFragment: Fragment() {
    lateinit var noTrackAdapter: NoTracksAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        noTrackAdapter = NoTracksAdapter(context)
        linearLayoutManager = LinearLayoutManager(context)

        return inflater.inflate(R.layout.fragment_no_tracks, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        no_tracks_view.layoutManager = GridLayoutManager(context, 1)
        no_tracks_view.adapter = noTrackAdapter

        print("NO DATAS")
    }



    companion object {
        fun newInstance(): NoTracksFoundFragment {
            return NoTracksFoundFragment()
        }
    }

}