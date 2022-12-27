package org.isen.gasfinder.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import org.apache.logging.log4j.kotlin.Logging
import org.apache.logging.log4j.kotlin.logger


data class GasStationInformation(val data:GasStation, val lastUpdatedOther:Long, val ttl:Int) {

    class Deserializer: ResponseDeserializable<GasStationInformation> {
        override fun deserialize(content: String): GasStationInformation {
            println(content)
            return Gson().fromJson(content, GasStationInformation::class.java)

        }
    }
}


data class GasStation(val gasStations:List<GasStationInfo>)

data class GasStationInfo(
        val id:String, // id unique de la station
        val cp:String, // code postal de la station
        val pop:String, // si c'est sur autoroute ou route simple
        val adresse:String, // l'adresse de la station
        val ville:String, //la ville de la station
        //val schedule:List<String>, //horaires détaillé de la station
        val geom: GeoPoint, // localisation de la station
        val prix_id:String, //id du carburant
        val prix_valeur:Float, //prix du carburant
        val prix_nom:String,//nom carburant
        val services_service: List<String> // caractéristiques de la station
        )
