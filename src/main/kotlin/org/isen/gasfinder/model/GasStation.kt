package org.isen.gasfinder.model

import kotlin.properties.Delegates

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

    class Gas() {
        var price by Delegates.notNull<Double>()
        lateinit var type : GasType
        constructor(id: String, price: Double) : this() {
            this.price = price
            type = getGasTypeFromString(id)
        }
        override fun toString(): String {
            return type.name + " : " + price
        }

        enum class GasType(val id: String){
            GAZOLE("1"),
            SP95("2"),
            E85("3"),
            GPLC("4"),
            E10("5"),
            SP98("6")
        }

        fun getGasTypeFromString(id: String): GasType {
            return when(id) {
                "1" -> GasType.GAZOLE
                "2" -> GasType.SP95
                "3" -> GasType.E85
                "4" -> GasType.GPLC
                "5" -> GasType.E10
                "6" -> GasType.SP98
                else -> throw IllegalArgumentException("Unknown gas type")
            }
        }
    }

    fun getSearchableString(): String {
        var searchableString = ""
        for(gas in gas) searchableString += gas.type.name + " "
        for(service in services) searchableString += service.value + " "
        return searchableString + " $geoPoint " + if(isOnHighway) " Autoroute" else " Route"
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