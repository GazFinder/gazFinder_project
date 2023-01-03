
package org.isen.gasfinder.view



import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStation
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.*


class GasStationListView (val controller: GasStationController):IGasStationView, ActionListener {

    companion object : Logging

    private var gasStationList: JList<GasStation> = JList()
    private val frame: JFrame

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
        contentPane.add(makeNorthArea(), BorderLayout.NORTH)

        return contentPane
    }

    private fun makeNorthArea(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout()
        result.add(JLabel("Station :"), BorderLayout.WEST)
        result.add(gasStationList, BorderLayout.CENTER)
        return result
    }

    override fun display() {
        frame.isVisible = true
    }

    override fun close() {
        this.controller.closeView()
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        if (evt.newValue is List<*>){
            logger.info("receive GasStationInformation data")
            val list = evt.newValue as List<GasStation>
            val model = DefaultListModel<GasStation>()
            list.forEach { model.addElement(it) }
            gasStationList.model = model
            gasStationList.cellRenderer = StationInfoCellRender()
        }
    }


    override fun actionPerformed(e: ActionEvent) {

    }

}





