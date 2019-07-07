package com.example.androiddeezer.models

import org.json.JSONObject

class Album {
    private var id: String? = null
    private var title: String? = null
    private var cover_small: String? = null
    private var genre_id: Int? = null
    private var duration: Int? = null
    private var artist: Artist? = null


    constructor(JObject: JSONObject){
        if(JObject.has("id"))
            this.id = JObject.getString("id")
        if(JObject.has("title"))
            this.title = JObject.getString("title")
        if(JObject.has("cover_small"))
            this.cover_small = JObject.getString("cover_small")
        if(JObject.has("duration"))
            this.duration = JObject.getInt("duration")
        if(JObject.has("artist")){
            var artistJSON = JObject.getJSONObject("artist")
            artist = Artist(artistJSON)
        }
    }

    public fun getCoverSmall(): String? {
        return cover_small
    }
}