package org.isen.gasfinder.view



import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.Fields
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.Record
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.Image
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import java.lang.IllegalStateException
import javax.swing.Action
import javax.swing.DefaultComboBoxModel
import javax.swing.ImageIcon
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.WindowConstants
import javax.swing.border.EmptyBorder


class GasStationListView (val controller: GasStationController):IGasStationView, ActionListener {

    companion object : Logging

    private var gasStationList: JComboBox<Record> = JComboBox<Record>().apply {
        this.addActionListener(this@GasStationListView)
        this.renderer = StationInfoCellRender()
    }


    private val frame: JFrame
    private val labelStationSelected_CP = JLabel()
    private val labelStationSelected_Pop = JLabel()
    private val labelStationSelected_GasName = JLabel()
    private val labelStationSelected_GasPrice = JLabel()
    private val labelStationSelected_Services = JLabel()
    private val labelStationSelected_City = JLabel()

    init {

        frame = JFrame().apply {
            isVisible = false
            contentPane = makeGUI()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            this.title = title
            this.preferredSize = Dimension(800, 500)
            this.pack()
        }
        this.controller.registerViewToGasData(this)
    }


    private fun makeGUI(): JPanel {
        val contentPane = JPanel()

        contentPane.layout = BorderLayout()
        contentPane.add(createVelibComboBox(), BorderLayout.NORTH)
        contentPane.add(createStationInformationPanel(), BorderLayout.WEST)

        return contentPane
    }


    private fun createVelibComboBox(): JPanel {
        val contentPane = JPanel()
        contentPane.layout = BorderLayout()
        gasStationList.border = EmptyBorder(5, 5, 5, 5)
        contentPane.add(gasStationList, BorderLayout.CENTER)

        return contentPane
    }

    private fun createStationInformationPanel(): JPanel {
        val contentPane = JPanel()
        contentPane.layout = GridLayout(6, 2)
        contentPane.preferredSize = Dimension(250, 100)

        contentPane.add(JLabel("ville: "))
        contentPane.add(labelStationSelected_City)

        contentPane.add(JLabel("code postal: "))
        contentPane.add(labelStationSelected_CP)

        contentPane.add(JLabel("Route ou Autoroute : "))
        contentPane.add(labelStationSelected_Pop)

        contentPane.add(JLabel("Nom du carburant : "))
        contentPane.add(labelStationSelected_GasName)

        contentPane.add(JLabel("Prix du carburant: "))
        contentPane.add(labelStationSelected_GasPrice)

        contentPane.add(JLabel("Service de la Station : "))
        contentPane.add(labelStationSelected_Services)

        return contentPane
    }

    override fun display() {
        frame.isVisible = true
    }

    override fun close() {
        this.controller.closeView()
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        if (evt.newValue is GasStation) {
            logger.info("receive GasStationInformation data")
            gasStationList.model = DefaultComboBoxModel<Record>((evt.newValue as GasStation).records.toTypedArray())

                    labelStationSelected_City.text = (gasStationList.selectedItem as Record).fields.ville
                    labelStationSelected_CP.text = (gasStationList.selectedItem as Record).fields.cp
                    labelStationSelected_Pop.text =
                        if ((gasStationList.selectedItem as Record).fields.pop == "R") "Route" else "Autoroute"
                    labelStationSelected_GasName.text = (gasStationList.selectedItem as Record).fields.prix_nom
                    labelStationSelected_GasPrice.text =
                        (gasStationList.selectedItem as Record).fields.prix_valeur.toString()
                    labelStationSelected_Services.text = (gasStationList.selectedItem as Record).fields.services_service
                }
            else {
                logger.info("unknown data")
            }
        }


    override fun actionPerformed(e: ActionEvent) {
        if (e.source is JComboBox<*>) {
            logger.info(
                "click on combo with index: ${gasStationList.selectedIndex} and "
                        + "value ${gasStationList.selectedItem}"
            )
            controller.selectedStation(gasStationList.model.getElementAt(gasStationList.selectedIndex).fields.id)
        }
    }

}




