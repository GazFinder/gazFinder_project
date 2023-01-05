package org.isen.gasfinder.view.map

import org.isen.gasfinder.model.GeoPoint
import org.isen.gasfinder.view.GasStationMapView
import java.awt.Image
import java.awt.geom.Point2D.distance
import java.beans.PropertyChangeSupport
import java.net.URL
import javax.imageio.ImageIO
import kotlin.math.*
import kotlin.properties.Delegates

class StationsMap {
    companion object {
        const val PREFIX_URL = "https://maps.geoapify.com/v1/staticmap"
        const val API_KEY = "f0775f620b0444fe8ea664f71854cda1"
    }

    private val pcs = PropertyChangeSupport(this)
    private var height = 1080
    private var width = 1920

    private var stations = mutableListOf<Triple<Double, Double, String>>()
    private var selected : Triple<Double, Double, String>? = null
    private var image: Image? by Delegates.observable(null) { _, _, newValue ->
        pcs.firePropertyChange("Image", null, newValue)
    }
    fun registerToMapUpdate(listener: GasStationMapView) {
        pcs.addPropertyChangeListener(listener)
    }

    fun addStations(stations: List<Triple<Double, Double, String>>) {
        this.stations.addAll(stations)
        generate()
    }

    fun setSize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }
    fun clearStations() {
        stations.clear()
    }

    fun setSelectedStation(station: GeoPoint) {
        selected = Triple(station.latitude, station.longitude, station.address!!)
        generate()
    }

    private fun renderMarker(coords:Pair<Double,Double>):String {
        return "lonlat:${coords.first},${coords.second};type:awesome;color:red;size:large;" +
                "icon:local_gas_station;icontype:material;iconsize:small;textsize:small"
    }

    private fun renderSelectedMarker(cords:Pair<Double,Double>):String {
        return "lonlat:${cords.first},${cords.second};type:awesome;color:cyan;size:large;" +
                "icon:local_gas_station;icontype:material;iconsize:small;textsize:small"
    }

    private fun generate() {
        if(stations.isEmpty()) {
            image = null
            return
        }
        val centerOfMapY = stations.map { it.first }.average()
        val centerOfMapX = stations.map { it.second }.average()

        // Calculating the zoom in order to include all the stations
        val maxDistance = stations.map { distance(it.second, it.first, centerOfMapX, centerOfMapY) }.max()
        val zp = sqrt((height * height + width * width).toDouble()) * 0.002
        val zq = 1 - (maxDistance.pow(1/4.5) / 5.8.pow(1/4.5))
        val zoom = zp + (10 * zq)

        val urlBuffer = StringBuilder(
            "$PREFIX_URL?style=dark-matter-yellow-roads&width=$width&height=$height" +
                    "&center=lonlat:$centerOfMapX,$centerOfMapY&zoom=$zoom"
        )

        urlBuffer.append("&marker=")
            .append(stations.joinToString("|") {
                if(it == selected) renderSelectedMarker(it.second to it.first)
                else renderMarker(it.second to it.first)
            })
        urlBuffer.append("&apiKey=$API_KEY")

        val url = URL(urlBuffer.toString())
        image = ImageIO.read(url)
    }
}