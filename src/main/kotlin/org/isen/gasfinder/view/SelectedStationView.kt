package org.isen.gasfinder.view

import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.IGasStationModel
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.*

class SelectedStationView (private val controller: GasStationController, private val station: GasStation):IGasStationView, ActionListener {
    companion object : Logging

    private val frame: JFrame
    private var selectedGasStation: GasStation = station

    init {
        controller.registerView(this, listOf(IGasStationModel.DATATYPE_STATIONS, IGasStationModel.DATATYPE_STATION_SELECTED))
        frame = JFrame("SelectedStationView").apply {
            isVisible = false
            makeGUI(this)

            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            this.title = title
            this.preferredSize = Dimension(800, 220)
            this.pack()
        }
    }

    private fun makeGUI (frame: JFrame): JFrame {
        frame.add(makeTitleArea(), BorderLayout.NORTH)
        frame.add(makeIconArea(), BorderLayout.WEST)
        frame.add(makeCenterArea(), BorderLayout.CENTER)
        
        return frame
    }
    
    private fun makeTitleArea (): JPanel {
        val titlePanel: JPanel = JPanel()
        titlePanel.layout = BorderLayout()
        titlePanel.preferredSize = Dimension(800, 70)
//        titlePanel.background = java.awt.Color(247,247,247)

        val title = JLabel("Station Information")
        title.font = title.font.deriveFont(30f)
        title.verticalAlignment = JLabel.CENTER
        title.horizontalAlignment = JLabel.CENTER

        titlePanel.add(title)
        
        return titlePanel
    }
    
    private fun makeIconArea (): JPanel {
        val iconPanel: JPanel = JPanel()
        iconPanel.layout = BorderLayout()
        iconPanel.preferredSize = Dimension(150, 150)
//        iconPanel.background = java.awt.Color(247,247,247)

        val icon = JLabel()
        icon.icon = ImageIcon("src/main/resources/station_icon.png")
        icon.verticalAlignment = JLabel.CENTER
        icon.horizontalAlignment = JLabel.CENTER

        iconPanel.add(icon)

        return iconPanel
    }
    
    private fun makeCenterArea (): JPanel {
        val centerPanel: JPanel = JPanel()
        centerPanel.layout = GridLayout(2, 1)
        centerPanel.preferredSize = Dimension(650, 150)
//        centerPanel.background = java.awt.Color(247,247,247)

        centerPanel.add(makeAddressArea())
        centerPanel.add(makeBottomArea())

        return centerPanel
    }

    private fun makeAddressArea (): JPanel {
        val addressInfoPanel: JPanel = JPanel()

        // ADDRESS
        val address = JLabel()
        address.text = "<HTML>${selectedGasStation.geoPoint.address}, ${selectedGasStation.geoPoint.postalCode} ${selectedGasStation.geoPoint.city}<br>Position: "
        if (selectedGasStation.isOnHighway) {
            address.text += "On highway</HTML>"
        } else {
            address.text += "Not on highway</HTML>"
        }
        address.font = address.font.deriveFont(15f)

        addressInfoPanel.add(address)
        addressInfoPanel.layout = FlowLayout()

        return addressInfoPanel
    }

    private fun makeBottomArea (): JPanel {
        val bottomPanel: JPanel = JPanel()
        bottomPanel.layout = GridLayout(1, 2)
//        bottomPanel.preferredSize = Dimension(550, 380)
//        bottomPanel.background = java.awt.Color(247,247,247)

        bottomPanel.add(makeGasArea())
        bottomPanel.add(makeServicesArea())

        return bottomPanel
    }

    private fun makeGasArea (): JPanel {
        val gasPanel: JPanel = JPanel()
        gasPanel.layout = FlowLayout()

        for (gas in selectedGasStation.gas) {
            val gasLabel: JLabel = JLabel()
            gasLabel.text = "${gas.type} : ${gas.price}€"
            gasLabel.font = gasLabel.font.deriveFont(15f)
            gasLabel.verticalAlignment = JLabel.CENTER
            gasLabel.horizontalAlignment = JLabel.LEADING

            gasPanel.add(gasLabel)
        }

        return gasPanel
    }

    private fun makeServicesArea (): JPanel {
        val servicesPanel: JPanel = JPanel()
        servicesPanel.layout = FlowLayout()

        for (service in selectedGasStation.services) {
            val serviceLabel: JLabel = JLabel()
            serviceLabel.text = service.value
            serviceLabel.font = serviceLabel.font.deriveFont(15f)
            serviceLabel.verticalAlignment = JLabel.CENTER
            serviceLabel.horizontalAlignment = JLabel.LEADING

            servicesPanel.add(serviceLabel)
        }

        return servicesPanel
    }

    override fun display() {
        frame.isVisible = true
    }

    override fun close() {
        frame.isVisible = false
    }

    override fun propertyChange(evt: PropertyChangeEvent?) {
//        selectedGasStation = evt?.newValue as GasStation
    }

    override fun actionPerformed(e: ActionEvent?) {
        TODO("Not yet implemented")
    }
}