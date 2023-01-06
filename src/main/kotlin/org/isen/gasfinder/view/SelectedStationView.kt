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

class SelectedStationView (private val station: GasStation):IGasStationView, ActionListener {
    companion object : Logging

    private val frame: JFrame
    private var selectedGasStation: GasStation = station

    init {
        frame = JFrame("SelectedStationView").apply {
            isVisible = true
            makeGUI(this)

            defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
            this.title = title
            this.preferredSize = Dimension(500, 600)
            this.pack()
        }
    }

    private fun makeGUI (frame: JFrame): JFrame {
        //frame.add(makeTitleArea(), BorderLayout.NORTH)
        frame.add(makeIconArea(), BorderLayout.WEST)
        frame.add(makeCenterArea(), BorderLayout.CENTER)
        
        return frame
    }
    
    private fun makeTitleArea (): JPanel {
        val titlePanel: JPanel = JPanel()
        titlePanel.layout = BorderLayout()
        //titlePanel.preferredSize = Dimension(800, 70)
        titlePanel.background = Color.WHITE

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
        //iconPanel.preferredSize = Dimension(150, 150)
        iconPanel.background = Color.WHITE
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
        centerPanel.layout = GridLayout(3, 1)
        //centerPanel.preferredSize = Dimension(650, 150)
        centerPanel.background = Color.WHITE
//        centerPanel.background = java.awt.Color(247,247,247)

        centerPanel.add(makeAddressArea())
        centerPanel.add(makeGasArea())
        centerPanel.add(makeServicesArea())

        return centerPanel
    }

    private fun makeAddressArea (): JPanel {
        val addressInfoPanel: JPanel = JPanel()
        addressInfoPanel.background = Color.WHITE
        // ADDRESS
        val address = JLabel()
        address.border = BorderFactory.createTitledBorder("Address")
        address.background = Color.WHITE
        address.text = "<HTML><h3>${selectedGasStation.geoPoint.address}</h3> <p>${selectedGasStation.geoPoint.postalCode} ${selectedGasStation.geoPoint.city}</p><br>"
        if (selectedGasStation.isOnHighway) {
            address.text += "On highway</HTML>"
        } else {
            address.text += "</HTML>"
        }
        address.font = address.font.deriveFont(15f)

        addressInfoPanel.add(address)
        addressInfoPanel.layout = FlowLayout()

        return addressInfoPanel
    }

    private fun makeBottomArea (): JPanel {
        val bottomPanel: JPanel = JPanel()
        bottomPanel.layout = GridLayout(1, 2)
        bottomPanel.background = Color.WHITE
        bottomPanel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        bottomPanel.add(makeGasArea())
        bottomPanel.add(makeServicesArea())

        return bottomPanel
    }

    private fun makeGasArea (): JPanel {
        val gasPanel: JPanel = JPanel()
        gasPanel.layout = GridLayout(selectedGasStation.gas.size, 1)
        gasPanel.border = BorderFactory.createTitledBorder("Gas")
        gasPanel.background = Color.WHITE
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
        servicesPanel.layout = GridLayout(selectedGasStation.services.size, 1)
        servicesPanel.border = BorderFactory.createTitledBorder("Services")
        servicesPanel.background = Color.WHITE
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
        this.close()
    }

    override fun actionPerformed(e: ActionEvent?) {
        TODO("Not yet implemented")
    }
}