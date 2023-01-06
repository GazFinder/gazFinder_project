
package org.isen.gasfinder.view


import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.GeoPoint
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.view.map.StationsMap
import org.isen.gasfinder.view.map.StationsMapPanel
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Image
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.*

class GasStationMapView(controller: GasStationController) : IGasStationView, ActionListener{
    companion object : Logging
    private val frame: JFrame
    private val mapPanel: StationsMapPanel = StationsMapPanel()
    private var image : Image? = null
    private var stationPoints : List<GeoPoint>? = null
    private var stationsMap : StationsMap? = null
    private val listPanel: GasStationListPanel = GasStationListPanel(controller)

    init {
        controller.registerView(this, listOf(IGasStationModel.DATATYPE_STATIONS, IGasStationModel.DATATYPE_STATION_SELECTED))
        stationsMap = StationsMap()
        stationsMap!!.registerToMapUpdate(this)

        frame = JFrame("GasStationMapView").apply {
            isVisible = false
            contentPane = makeGUI()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            extendedState = JFrame.MAXIMIZED_BOTH
            minimumSize = Dimension(1000, 1200)
            this.pack()
        }
    }

    private fun makeGUI(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout(20, 20)
        result.add(mapPanel, BorderLayout.CENTER)
        result.add(listPanel, BorderLayout.WEST)
        listPanel.preferredSize = Dimension(500, 0)
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
            if(gasStationList.isNotEmpty()) {
                Thread {
                    stationPoints = gasStationList.map { gasStation -> gasStation.geoPoint }
                    stationsMap?.setSize(mapPanel.width, mapPanel.height)
                    stationsMap?.clearStations()
                    stationsMap?.addStations(stationPoints!!.map { geoPoint ->
                        Triple(
                            geoPoint.latitude,
                            geoPoint.longitude,
                            geoPoint.address!!
                        )
                    })
                }.start()
            }
            mapPanel.setLoading(true)
        }
        if(evt.propertyName == IGasStationModel.DATATYPE_STATION_SELECTED){
            Thread {
                stationsMap?.setSize(mapPanel.width, mapPanel.height)
                val selectedStation = evt.newValue as GasStation?
                stationsMap?.setSelectedStation(selectedStation?.geoPoint)
            }.start()
        }
        if(evt.propertyName == "Image" && evt.newValue != null){
            image = evt.newValue as Image
            mapPanel.setStationsMap(image!!)
            mapPanel.setLoading(false)
        }
    }

    override fun actionPerformed(p0: ActionEvent?) {
        //TODO("Not yet implemented")
    }
}