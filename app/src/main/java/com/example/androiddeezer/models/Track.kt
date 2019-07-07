package com.example.androiddeezer.models

import org.json.JSONObject

class Track {

    private var idTrack : String = ""
    private var titleTrack : String = ""
    private var duration: String = ""
    private var position: Int = 0
    private var rank: String = ""
    private var preview: String = ""
    private lateinit var artistTrack: Artist
    private lateinit var albumTrack: Album

    constructor()

    constructor(JObject: JSONObject){
        if(JObject.has("id"))
            idTrack = JObject.getString("id")
        if(JObject.has("title"))
            titleTrack = JObject.getString("title")
        if(JObject.has("duration"))
            duration = JObject.getString("duration")
        if(JObject.has("position"))
            position = JObject.getInt("position")
        if(JObject.has("rank"))
            rank = JObject.getString("rank")
        if(JObject.has("preview"))
            preview = JObject.getString("preview")
        if(JObject.has("artist")){
            var artistJSON = JObject.getJSONObject("artist")
            artistTrack = Artist(artistJSON)
        }
        if(JObject.has("album")){
            var albumJSON = JObject.getJSONObject("album")
            albumTrack = Album(albumJSON)
        }
    }

    public fun getAlbum(): Album{
        return albumTrack
    }
    public fun getArtist(): Artist{
        return artistTrack
    }
    public fun getPreview(): String{
        return preview
    }
}