package org.isen.gasfinder.controller



import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.model.SearchParameters
import org.isen.gasfinder.view.IGasStationView

class GasStationController(val model: IGasStationModel) {

    val views = mutableListOf<IGasStationView>()
    var source: IGasStationModel.DataSources = IGasStationModel.DataSources.DATAECO

    fun handleStationSelection(id: String?) {
        model.changeCurrentSelection(id)
    }

    fun registerView(v: IGasStationView,datatypes: List<String>?) {
        if (!this.views.contains(v)) {
            this.views.add(v)
            if (datatypes == null){
                this.model.register(null, v)
            }
            else for (datatype in datatypes){
                this.model.register(datatype,v)
            }
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

    fun handleOpenSearch() {
        // TODO : open search view
        println("handleOpenSearch")
    }

    fun handleSearch(searchParameters : SearchParameters? = null) {
        this.model.searchGasStations(searchParameters, source)
    }

    fun handleSortByPrice() {
        //model.sortGasStationsByPrice()
        //mapView.displayGasStationsOnMap()
        //listView.displayGasStationsInList()
    }



    fun handleItinerarySelected() {
        //  val itinerary = itineraryInputView.getItineraryFromUser()
        //model.selectedItinerary = itinerary
        //mapView.displayGasStationsAlongItineraryOnMap()
        //listView.displayGasStationsAlongItineraryInList()
    }
}
