package org.isen.gasfinder.model

class GasStation(
    val geoPoint: GeoPoint,
    val name: String? = null,
    val gas: List<Gas>,
    val services: List<Service>,
    val isOnHighway: Boolean = false,
    val id: String
) {
    var distance: Double? = null

    override fun toString(): String {
        return "GasStation(geoPoint=$geoPoint, name=$name, gas=$gas, services=$services, isOnHighway=$isOnHighway, id=$id, distance=$distance)"
    }

    class Gas(val id: String, val name: String, val price: Double) {
        override fun toString(): String {
            return "$name, $price"
        }
    }


    enum class Service(val value: String) {
        SELL_GAS( "Vente de gaz domestique (Butane, Propane)"),
        AIR_PUMP( "Station de gonflage"),
        ATM("DAB (Distributeur automatique de billets)"),
        FOOD_STORE("Boutique alimentaire"),
        TRUCK_PARKING( "Piste poids lourds"),
        ATM24("Automate CB 24/24"),
        NON_FOOD_STORE("Boutique non alimentaire"),
        AUTOMATIC_WASH("Lavage automatique"),
        CASH_MACHINE("Carburant additivé"),
        PUBLIC_TOILETS("Toilettes publiques"),
        MANUAL_WASH("Lavage manuel"),
        TAKE_AWAY( "Restauration à emporter"),
        CAR_RENTAL("Location de véhicule"),
        PARCEL_DELIVERY( "Relais colis"),
        LAUNDRY( "Laverie"),
        ADDITIVES( "Vente d'additifs carburants"),
        RESTAURANT( "Restauration sur place"),
        REPAIR( "Services réparation / entretien"),
        WIFI("Wifi"),
        HEATING_OIL( "Vente de fioul domestique"),
        LAMP_OIL ("Vente de pétrole lampant"),
        BABY_AREA( "Espace bébé"),
        BAR("Bar"),
        ELECTRICITY( "Bornes électriques"),
        SHOWER("Douches"),
        CAMPER_PARKING("Aire de camping-cars"),
        GNV("GNV"),
        UNKNOWN("Unknown");
    }

    companion object {
        fun getServiceFromString(service: String): Service {
            return Service.values().find { it.value == service } ?: Service.UNKNOWN
        }
    }
}