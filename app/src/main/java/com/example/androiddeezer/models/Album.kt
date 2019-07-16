package com.example.androiddeezer.models

import org.json.JSONObject

class Album {
    private var id: String? = null
    private var title: String? = null
    private var cover_small: String? = null
    private var cover_medium: String? = null
    private var cover_big: String? = null
    private var cover: String? = null
    private var genre_id: Int? = null
    private var duration: Int? = null
    private var artist: Artist? = null
    private var release_date: String? = null


    constructor()

    constructor(JObject: JSONObject){
        if(JObject.has("id"))
            this.id = JObject.getString("id")
        if(JObject.has("title"))
            this.title = JObject.getString("title")
        if(JObject.has("cover_small"))
            this.cover_small = JObject.getString("cover_small")
        if(JObject.has("cover_medium"))
            this.cover_medium = JObject.getString("cover_medium")
        if (JObject.has("cover_big"))
            this.cover_big = JObject.getString("cover_big")
        if(JObject.has("cover"))
            this.cover = JObject.getString("cover")
        if(JObject.has("duration"))
            this.duration = JObject.getInt("duration")
        if(JObject.has("artist")){
            var artistJSON = JObject.getJSONObject("artist")
            artist = Artist(artistJSON)
        }
        if (JObject.has("release_date"))
            this.release_date = JObject.getString("release_date")
    }

    public fun getCoverSmall(): String? {
        return cover_small
    }
    public fun getCoverMedium(): String? {
        return cover_medium
    }
    public fun getCover(): String? {
        return cover
    }

    override fun toString(): String {
        return "Album(id=$id, title=$title, cover_medium=$cover_medium, release_date=$release_date, artist=$artist)"
    }
}