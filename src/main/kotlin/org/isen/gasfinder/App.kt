package org.isen.gasfinder

import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.model.SearchParameters
import org.isen.gasfinder.view.GasStationListView
import org.isen.gasfinder.view.GasStationMapView
import java.lang.Thread.sleep

fun main() {
    val model: IGasStationModel= GasStationModel()
    val controller = GasStationController(model)
    val listView = GasStationListView(controller)
    val mapView = GasStationMapView(controller)

    controller.displayViews()
    controller.handleSearch(SearchParameters("83", null, null, null, null))
    sleep(5000)
    controller.handleSearch(SearchParameters("toulon", null, null, null, null))

}
