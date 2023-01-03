package org.isen.gasfinder.model

class GeoPoint(
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val city: String? = null,
    val postalCode: String? = null
) {

    override fun toString(): String {
        return "$address $postalCode $city"
    }
}
