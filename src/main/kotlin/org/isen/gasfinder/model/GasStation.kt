package org.isen.gasfinder.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class GasStation( val records:List<Record>) {

    class Deserializer: ResponseDeserializable<GasStation> {
        override fun deserialize(content: String): GasStation {
            return Gson().fromJson(content, GasStation::class.java)

        }
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