package org.isen.gasfinder.model

class GasStationModel {
    private val gasStations = mutableListOf<GasStation>()
    var selectedItinerary: Itinerary? = null

    fun addGasStation(gasStation: GasStation) {
        gasStations.add(gasStation)
    }

    fun removeGasStation(gasStation: GasStation) {
        gasStations.remove(gasStation)
    }

    fun sortGasStationsByPrice() {
        gasStations.sortBy { it.pricePerGallon }
    }

    fun getGasStations(): List<GasStation> {
        return gasStations
    }

    fun searchGasStations(searchTerm: String): List<GasStation> {
        return gasStations.filter { it.services.contains(searchTerm) }
    }

    fun getGasStationsAlongItinerary(): List<GasStation> {
        val itinerary = selectedItinerary ?: return emptyList()
        return gasStations.filter { it.location in itinerary }
    }
}