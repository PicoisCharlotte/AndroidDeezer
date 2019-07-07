package com.example.androiddeezer.models

import org.json.JSONObject

class Artist {
    private var name: String? = null

    constructor()
    constructor(JObject: JSONObject){
        if(JObject.has("name"))
            name = JObject.getString("name")
    }
}
