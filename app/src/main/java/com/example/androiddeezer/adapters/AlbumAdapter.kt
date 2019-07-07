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
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumAdapter(val context: Context): Adapter<ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var albumList: MutableList<Album> = ArrayList()

    private lateinit var mListener: ItemClickCallback
    internal lateinit var inflater: LayoutInflater
    interface ItemClickCallback { fun onItemClick(position: Int) }

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val album = albumList[position]

        holder?.itemView?.album_title?.text = album.title
    }
}

/*class RobotAdapter(private val mContext: Context, private val mAdapterCallbackRobot: AdapterCallbackRobot) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var listRobots: MutableList<Robot> = ArrayList()
    private lateinit var mListener: ItemClickCallback
    internal lateinit var inflater: LayoutInflater

    private var onClickListenerVideo: View.OnClickListener? = null

    interface ItemClickCallback { fun onItemClick(position: Int) }
    fun setOnItemClickListener(itemClickCallback: ItemClickCallback) {
        mListener = itemClickCallback
    }

    fun setData(data: MutableList<Robot>) {
        listRobots = data
        notifyDataSetChanged()
    }

    fun addItem(item: Robot){
        listRobots.add(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rowView: View
        rowView = LayoutInflater.from(mContext).inflate(R.layout.item_robot, parent, false)
        return ViewHolder(rowView)

    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {

        onClickListenerVideo =  View.OnClickListener{ v -> mAdapterCallbackRobot.goToVideo(listRobots[position].idRobot) }

        val robot = listRobots[position]
        h.itemView.name.text = robot.name
        h.itemView.model.text = robot.model
        h.itemView.item.setOnClickListener{mAdapterCallbackRobot.onClickItem(robot)}
        h.itemView.btn_go_to_video.setOnClickListener(onClickListenerVideo)

    }*/
