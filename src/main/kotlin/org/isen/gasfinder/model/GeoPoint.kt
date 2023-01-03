package org.isen.gasfinder.model

class GeoPoint(
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val city: String? = null,
    val postalCode: String? = null
) {

    override fun toString(): String {
        return "GeoPoint(latitude=$latitude, longitude=$longitude, address=$address, city=$city, postalCode=$postalCode)"
    }
}
