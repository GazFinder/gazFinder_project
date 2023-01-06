package org.isen.gasfinder

import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.model.SearchParameters
import org.isen.gasfinder.view.GasStationMapView
import org.isen.gasfinder.view.SearchView
import java.lang.Thread.sleep

fun main() {
    val model: IGasStationModel= GasStationModel()
    val controller = GasStationController(model)
    val mapView = GasStationMapView(controller)
    val searchView = SearchView(controller)

    controller.displayViews()
    sleep(1000)
    controller.handleSearch(SearchParameters("france", null, null, null, null))
    sleep(7000)
    controller.handleSearch(SearchParameters("garéoult", null, null, null, null))
    sleep(7000)
    controller.handleSearch(SearchParameters("83", null, null, null, null))
    sleep(7000)
    controller.handleSearch(SearchParameters("paris", null, null, null, null))
    sleep(7000)
    controller.handleSearch(SearchParameters("limoges", null, null, null, null))
}
