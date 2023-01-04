package org.isen.gasfinder.model


import java.awt.Image
import java.net.URL
import javax.imageio.ImageIO


class CartoHelper(val position:Pair<Double,Double>, val zoom:Double, val size:Pair<Int,Int>) {
    companion object {
        const val PREFIX_URL = "https://maps.geoapify.com/v1/staticmap"
        const val API_KEY = "f0775f620b0444fe8ea664f71854cda1"
    }

    private fun makeMyMaker(coor:Pair<Double,Double>):String {
        return "lonlat:${coor.first},${coor.second};type:awesome;color:red;size:large;" +
                "icon:directions_bike;icontype:material;iconsize:small;textsize:small"
    }

    private fun makeOtherMaker(coor:Pair<Double,Double>):String {
        return "lonlat:${coor.first},${coor.second};type:awesome;color:red;size:large;" +
                "icon:directions_bike;icontype:material;iconsize:small;textsize:small"
    }

    fun generate(my: Pair<Double, Double>, other: List<Pair<Double, Double>>) : Image {
        val url_buffer = StringBuilder("$PREFIX_URL?style=osm-carto&width=${size.first}&height=${size.second}" +
                "&center=lonlat:${position.first},${position.second}&zoom=$zoom")

        url_buffer.append("&marker=")
                .append(makeMyMaker(my))
                .append(other.joinToString("") {
                    "|${makeOtherMaker(it)}"
                })
                .append("&apiKey=$API_KEY")

        val url = URL(url_buffer.toString())

        return ImageIO.read(url)

        }

    }



