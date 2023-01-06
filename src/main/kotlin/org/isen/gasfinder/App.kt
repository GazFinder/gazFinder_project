package org.isen.gasfinder

import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.model.SearchParameters
import org.isen.gasfinder.view.GasStationMapView
import org.isen.gasfinder.view.GasStationSearchView
import org.isen.gasfinder.view.SelectedStationView
import java.lang.Thread.sleep

fun main() {
    val model: IGasStationModel= GasStationModel()
    val controller = GasStationController(model)
    val mapView = GasStationMapView(controller)
    val searchView = GasStationSearchView(controller)
    controller.displayViews()
    controller.handleSearch(SearchParameters("toulon", null, null, null, null))
}
