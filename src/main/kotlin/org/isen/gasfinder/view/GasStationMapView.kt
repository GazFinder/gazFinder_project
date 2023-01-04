
package org.isen.gasfinder.view


import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.CartoHelper
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.GeoPoint
import org.isen.gasfinder.model.IGasStationModel
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Image
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import java.lang.IllegalStateException
import javax.swing.*

class GasStationMapView(val controller: GasStationController) : IGasStationView, ActionListener{
    companion object : Logging

    private val frame: JFrame
    private var label_image = JLabel("No Data Available", JLabel.CENTER)
    private var stationPoints : List<GeoPoint>? = null

    init {
        controller.registerViewToCartoData(this)
        frame = JFrame("GasStationMapView").apply {
            isVisible = false
            contentPane = makeGUI()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            extendedState = JFrame.MAXIMIZED_BOTH;
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

    override fun propertyChange(evt: PropertyChangeEvent?) {
        logger.debug("property change")
        if (evt?.propertyName == IGasStationModel.DATATYPE_STATIONS) {
            val gasStationList = evt.newValue as List<GasStation>
            stationPoints = gasStationList.map { gasStation -> gasStation.geoPoint }
        }
    }

    fun generateMap() {


        /*
        return if(current != null) {
            val cartoHelper = CartoHelper(Pair(2.348496,48.853495), 11.9461, sizeCartoImage)
            cartoHelper.generate(
                Pair(current.geoPoint.longitude, current.geoPoint.latitude),
                proximity
            )
        } else {
            throw IllegalStateException("Current Station is null")
        }

        label_image.icon = ImageIcon(Image)
        label_image.repaint()*/
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        if (evt.newValue is Image) {

            logger.info("receive Image data")
            if (evt?.propertyName == IGasStationModel.DATATYPE_STATION_SELECTED) {
                label_image.icon = ImageIcon(evt.newValue as Image)
                label_image.repaint()
            } else {
                logger.info("unknown data")
            }
        }
    }



    override fun actionPerformed(p0: ActionEvent?) {
        //TODO("Not yet implemented")
    }
}