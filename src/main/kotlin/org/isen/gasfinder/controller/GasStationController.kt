package org.isen.gasfinder.controller

import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.view.*

class GasStationController(val model: IGasStationModel) {

    val views = mutableListOf<IGasStationView>()


    fun loadGasStationInformation() {
        this.model.findGasStationInformation()
    }


    fun selectedStation(id: Long) {
        model.changeCurrentSelection(id)
    }

    fun registerViewToGasData(v: IGasStationView) {
        if (!this.views.contains(v)) {
            this.views.add(v)
            this.model.register(IGasStationModel.DATATYPE_STATION, v)
        }
    }

    fun registerViewToCartoData(v: IGasStationView) {
        if (!this.views.contains(v)) {
            this.views.add(v)
            this.model.register(IGasStationModel.DATATYPE_CARTO, v)
        }
    }


    fun displayViews() {
        views.forEach() {
            it.display()
        }
    }

    fun closeView() {
        views.forEach() {
            it.close()
        }
    }


    fun handleSearch() {
        // val searchTerm = searchInputView.getSearchTermFromUser()
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
        //  val itinerary = itineraryInputView.getItineraryFromUser()
        //model.selectedItinerary = itinerary
        //mapView.displayGasStationsAlongItineraryOnMap()
        //listView.displayGasStationsAlongItineraryInList()
    }
}