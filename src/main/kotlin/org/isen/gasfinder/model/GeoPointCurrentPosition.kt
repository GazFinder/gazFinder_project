package org.isen.gasfinder.model

import com.github.kittinunf.fuel.httpGet

class GeoPointCurrentPosition : GeoPoint(0.0, 0.0) {
    private val URLwKey = "https://api.ipgeolocation.io/ipgeo?apiKey=d83c78f9417e45b3a6ed169b9056861c"
    init {
        val jsonString = URLwKey.httpGet().responseString().third.get()
        val json = org.json.JSONObject(jsonString)
        this.latitude = json.getDouble("latitude")
        this.longitude = json.getDouble("longitude")
    }
}