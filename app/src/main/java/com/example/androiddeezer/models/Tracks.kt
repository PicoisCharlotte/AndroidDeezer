package com.example.androiddeezer.models

import org.json.JSONObject

class Tracks {
    private var id: Int? = null
    var title: String? = null
    var link: String? = null
    var duration: Int? = null
    var explicit_lyrics: Boolean = false
    var artist: Artist? = null

    constructor(JObject: JSONObject) {
        if (JObject.has("id"))
            this.id = JObject.getInt("id")
        if (JObject.has("title"))
            this.title = JObject.getString("title")
        if(JObject.has("link"))
            this.link = JObject.getString("link")
        if(JObject.has("duration"))
            this.duration = JObject.getInt("duration")
        if(JObject.has("explicit_lyrics"))
            this.explicit_lyrics = JObject.getBoolean("explicit_lyrics")
        if(JObject.has("artist")) {
            val artistJSON = JObject.getJSONObject("artist")
            artist = Artist(artistJSON)
        }
    }


}