package org.isen.gasfinder.view

import org.isen.gasfinder.model.GasStationModel

class GasStationMapView(val model: GasStationModel.GasStationModel) {
    fun displayGasStationsOnMap(map: Map) {
        val gasStations = model.getGasStations()
        for (gasStation in gasStations) {
            map.addMarker(gasStation.location)
        }
    }

    fun displayGasStationsAlongItineraryOnMap(map: Map) {
        val gasStations = model.getGasStationsAlongItinerary()
        for (gasStation in gasStations) {
            map.addMarker(gasStation.location, color = Map.MarkerColor.GREEN)
        }
    }

    fun displaySearchResultsOnMap(map: Map, searchTerm: String) {
        val gasStations = model.searchGasStations(searchTerm)
        for (gasStation in gasStations) {
            map.addMarker(gasStation.location, color = Map.MarkerColor.BLUE)
        }
    }
}