package org.isen.gasfinder.controller

import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.view.GasStationListView
import org.isen.gasfinder.view.GasStationMapView
import org.isen.gasfinder.view.ItineraryInputView
import org.isen.gasfinder.view.SearchInputView

class GasStationController(
    private val model: GasStationModel,
    private val mapView: GasStationMapView,
    private val listView: GasStationListView,
    private val itineraryInputView: ItineraryInputView,
    private val searchInputView: SearchInputView
) {
    fun handleSearch() {
        val searchTerm = searchInputView.getSearchTermFromUser()
        //mapView.displaySearchResultsOnMap(searchTerm)
        //listView.displaySearchResultsInList(searchTerm)
    }

    fun handleSortByPrice() {
        //model.sortGasStationsByPrice()
        //mapView.displayGasStationsOnMap()
        //listView.displayGasStationsInList()
    }

    fun handleGasStationSelected(gasStation: GasStation) {
        //model.selectedGasStation = gasStation
        //mapView.displayGasStationDetailsOnMap(gasStation)
        //listView.displayGasStationDetailsInList(gasStation)
    }

    fun handleItinerarySelected() {
        val itinerary = itineraryInputView.getItineraryFromUser()
        model.selectedItinerary = itinerary
        //mapView.displayGasStationsAlongItineraryOnMap()
        //listView.displayGasStationsAlongItineraryInList()
    }
}
