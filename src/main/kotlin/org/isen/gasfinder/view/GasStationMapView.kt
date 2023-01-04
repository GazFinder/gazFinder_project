
package org.isen.gasfinder.view


import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.GeoPoint
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.view.map.StationsMap
import java.awt.BorderLayout
import java.awt.Image
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.*

class GasStationMapView(val controller: GasStationController) : IGasStationView, ActionListener{
    companion object : Logging

    private val frame: JFrame
    private var label_image = JLabel("No Data Available", JLabel.CENTER)
    private var image : Image? = null
    private var stationPoints : List<GeoPoint>? = null
    private var stationsMap : StationsMap? = null
    init {
        controller.registerViewToCartoData(this)
        stationsMap = StationsMap()
        stationsMap!!.registerToMapUpdate(this)

        frame = JFrame("GasStationMapView").apply {
            isVisible = false
            contentPane = makeGUI()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            extendedState = JFrame.MAXIMIZED_BOTH
            this.pack()
        }
    }

    private fun makeGUI(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout(20, 20)
        result.add(label_image, BorderLayout.CENTER)
        return result
    }

    override fun display() {
        frame.isVisible = true
    }

    override fun close() {
        frame.isVisible = false
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        logger.debug("property change")
        if (evt.propertyName == IGasStationModel.DATATYPE_STATIONS) {
            val gasStationList = evt.newValue as List<GasStation>
            stationPoints = gasStationList.map { gasStation -> gasStation.geoPoint }
            stationsMap?.setSize(frame.width, frame.height)
            stationsMap?.clearStations()
            stationsMap?.addStations(stationPoints!!.map { geoPoint -> Triple(geoPoint.latitude, geoPoint.longitude, geoPoint.address!!) })
        }
        if(evt.propertyName == "Image" && evt.newValue != null){
            image = evt.newValue as Image
            label_image.icon = ImageIcon(image)
            label_image.repaint()
        }
    }

    override fun actionPerformed(p0: ActionEvent?) {
        //TODO("Not yet implemented")
    }
}