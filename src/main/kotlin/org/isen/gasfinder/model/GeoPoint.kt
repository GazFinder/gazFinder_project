package org.isen.gasfinder.model

open class GeoPoint(
    var latitude: Double,
    var longitude: Double,
    val address: String? = null,
    val city: String? = null,
    val postalCode: String? = null
) {
    override fun toString(): String {
        return "$address $postalCode $city"
    }
}
