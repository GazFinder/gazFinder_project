package org.isen.gasfinder

import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.view.GasStationListView
import org.isen.gasfinder.view.TestView


class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val model: IGasStationModel= GasStationModel()
    val controller = GasStationController(model)

    val testView = TestView(controller)
    val velibInfoView = GasStationListView(controller)
    controller.displayViews()
    controller.loadGasStationInformation()

}
