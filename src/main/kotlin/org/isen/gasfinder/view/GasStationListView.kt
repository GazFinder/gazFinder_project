package org.isen.gasfinder.view

import org.isen.gasfinder.model.GasStationModel

class GasStationListView(val model: GasStationModel.GasStationModel) {
    fun displayGasStationsInList(list: List) {
        val gasStations = model.getGasStations()
        for (gasStation in gasStations) {
            list.addItem(gasStation.toString())
        }
    }

    fun displayGasStationsAlongItineraryInList(list: List) {
        val gasStations = model.getGasStationsAlongItinerary()
        for (gasStation in gasStations) {
            list.addItem(gasStation.toString(), color = List.ItemColor.GREEN)
        }
    }

    fun displaySearchResultsInList(list: List, searchTerm: String){

    }
}