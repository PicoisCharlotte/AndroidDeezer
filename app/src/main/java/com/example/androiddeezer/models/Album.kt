package com.example.androiddeezer.models

import org.json.JSONObject

class Album {
    var id: Int? = null
    var title: String? = null
    var cover_medium: String? = null
    var cover_big: String? = null
    var release_date: String? = null
    var artist: Artist? = null

    constructor(JObject: JSONObject) {
        if (JObject.has("id"))
            this.id = JObject.getInt("id")
        if (JObject.has("title"))
            this.title = JObject.getString("title")
        if (JObject.has("cover_medium"))
            this.cover_medium = JObject.getString("cover_small")
        if (JObject.has("cover_big"))
            this.cover_big = JObject.getString("cover_big")
        if (JObject.has("release_date"))
            this.release_date = JObject.getString("release_date")
        if (JObject.has("artist")) {
            var artistJSON = JObject.getJSONObject("artist")
            artist = Artist(artistJSON)
        }
    }

    constructor()


    public fun getCoverMedium(): String? {
        return cover_medium
    }

    override fun toString(): String {
        return "Album(id=$id, title=$title, cover_medium=$cover_medium, release_date=$release_date, artist=$artist)"
    }
}