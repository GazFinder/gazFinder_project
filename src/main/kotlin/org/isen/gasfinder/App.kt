package org.isen.gasfinder

import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.view.GasStationListView


class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val model: IGasStationModel= GasStationModel()
    val controller = GasStationController(model)
    val listView = GasStationListView(controller)

    controller.displayViews()
    controller.loadGasStationInformation()

}
