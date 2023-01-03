package org.isen.gasfinder.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import org.apache.logging.log4j.kotlin.logger

data class GasStationJSONParser(val records:List<Record>) {

    class Deserializer: ResponseDeserializable<GasStationJSONParser> {
        override fun deserialize(content: String): GasStationJSONParser {
            return Gson().fromJson(content, GasStationJSONParser::class.java)
        }
    }
    data class Record(
        val fields:Fields
    )
    data class Fields(
        val adresse: String,
        val cp: String,
        val geom: List<Double>,
        val id: String,
        val pop: String,
        val prix_id: String,
        val prix_nom: String,
        val prix_valeur: Double,
        val services_service: String,
        val ville: String
    )
}

fun parseGasStationJSON(url: String): List<GasStation> {
    val (request, response, result) = url.httpGet().responseObject(GasStationJSONParser.Deserializer())
    val (data, error) = result
    //We regroup all the gas stations entry by ID*
    if(response.statusCode != 200) {
        logger("parseGasStationJSON").error("Error while parsing JSON : $error - ${response.statusCode}")
        throw error!!
    }
    val gasStationList = data?.records?.groupBy { it.fields.id }?.map { (id, records) ->

        //Detection of bad data
        if(records.size == 1 && records[0].fields.prix_id == null) {
            logger("parseGasStationJSON").warn("No data for gas station $id")
            return@map null
        }
        //We regroup all the gas by type
        val gasList = records.map { record ->
            GasStation.Gas(record.fields.prix_id, record.fields.prix_valeur)
        }

        //We regroup all the services by type enum
        var servicesList : List<GasStation.Service> = emptyList()
        if(records[0].fields.services_service != null && records[0].fields.services_service.isNotEmpty()){
            val servicesListString = records[0].fields.services_service.split("//")
            servicesList = servicesListString.map { serviceStr -> GasStation.getServiceFromString(serviceStr) }
        }

        val geoPoint = GeoPoint(records[0].fields.geom[0], records[0].fields.geom[1], records[0].fields.adresse, records[0].fields.ville, records[0].fields.cp)

        val isOnHighway = records[0].fields.pop == "A"
        //We create the gas station
        GasStation(geoPoint, null, gasList, servicesList, isOnHighway, records[0].fields.id)
    }?.filterNotNull()
    return gasStationList ?: emptyList()
}
