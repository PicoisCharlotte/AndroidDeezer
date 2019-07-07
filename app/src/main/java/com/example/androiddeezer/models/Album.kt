package com.example.androiddeezer.models

import org.json.JSONObject

class Album {
    private var id: String? = null
    var title: String? = null
    var cover_small: String? = null
    var release_date: String? = null
    var artist: Artist? = null

    constructor(JObject: JSONObject) {
        if (JObject.has("id"))
            this.id = JObject.getString("id")
        if (JObject.has("title"))
            this.title = JObject.getString("title")
        if (JObject.has("cover_small"))
            this.cover_small = JObject.getString("cover_small")
        if (JObject.has("release_date"))
            this.release_date = JObject.getString("release_date")
        if (JObject.has("artist")) {
            var artistJSON = JObject.getJSONObject("artist")
            artist = Artist(artistJSON)
        }
    }




    public fun getCoverSmall(): String? {
        return cover_small
    }

    override fun toString(): String {
        return "Album(id=$id, title=$title, cover_small=$cover_small, release_date=$release_date, artist=$artist)"
    }
}