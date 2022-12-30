package org.isen.gasfinder.view/*
package org.isen.gasfinder.view

import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.GasStationModel

class GasStationListView(val model: GasStationModel) {
    fun displayGasStationsInList(list: List<GasStation>) {
        val gasStations = model.getGasStations()
        for (gasStation in gasStations) {
            //list.add(gasStation)
        }
    }

    fun displayGasStationsAlongItineraryInList(list: List<GasStation>) {
        val gasStations = model.getGasStationsAlongItinerary()
        for (gasStation in gasStations) {
            //list.addItem(gasStation.toString(), color = List.ItemColor.GREEN)
        }
    }

    fun displaySearchResultsInList(list: List<GasStation>, searchTerm: String){

    }
}*/
