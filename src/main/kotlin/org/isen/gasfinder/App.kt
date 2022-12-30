package org.isen.gasfinder

import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.model.IGasStationModel
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

    controller.displayViews()
    controller.loadGasStationInformation()

    /*
    val url = "https://data.economie.gouv.fr/explore/dataset/prix-carburants-fichier-instantane-test-ods-copie/download/?format=json&timezone=Europe/Berlin&lang=fr"
    val jsonData = URL(url).readText()
    val records = JSONArray(jsonData)
    val firstRecord = records.getJSONObject(0)
    val fields = firstRecord.getJSONObject("fields")
    println(fields)
     */

}
