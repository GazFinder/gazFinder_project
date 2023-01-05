
package org.isen.gasfinder.view



import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.GasStationModel
import org.isen.gasfinder.model.IGasStationModel
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.*


class GasStationListView (val controller: GasStationController):IGasStationView, ActionListener {

    companion object : Logging

    //We render the side-bar with a big search button and a list of gas stations results with a scroll bar
    private val frame: JFrame
    private val searchButton = JButton("Search")
    private val gasStationList = JList<GasStation>()
    private val gasStationListModel = DefaultListModel<GasStation>()
    private val gasStationListRenderer = StationInfoCellRender()
    private val gasStationListScrollPane = JScrollPane(gasStationList)

    init {
        controller.registerView(this, listOf(IGasStationModel.DATATYPE_STATIONS,IGasStationModel.DATATYPE_STATION_SELECTED))
        frame = JFrame("GasStationListView").apply {
            isVisible = false
            contentPane = makeGUI()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            this.title = title
            this.preferredSize = Dimension(500, 300)
            this.pack()
        }

        gasStationList.addListSelectionListener {
            if (!it.valueIsAdjusting) {
                val selectedValue = gasStationList.selectedValue
                if (selectedValue != null) {
                    controller.handleStationSelection(selectedValue.id)
                }else{
                    controller.handleStationSelection(null)
                }
            }
        }
    }

    private fun makeGUI(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout(20, 20)
        result.add(makeNorthArea(), BorderLayout.NORTH)
        result.add(makeCenterArea(), BorderLayout.CENTER)
        return result
    }

    private fun makeNorthArea(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout()
        searchButton.addActionListener(this)
        result.add(searchButton, BorderLayout.CENTER)
        return result
    }

    private fun makeCenterArea(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout()
        gasStationList.model = gasStationListModel
        gasStationList.cellRenderer = gasStationListRenderer
        result.add(gasStationListScrollPane, BorderLayout.CENTER)
        return result
    }

    override fun display() {
        frame.isVisible = true
    }

    override fun close() {
        frame.isVisible = false
    }

    override fun actionPerformed(e: ActionEvent?) {
        println("actionPerformed")
        logger.debug("call controller")
        controller.handleSearch()
    }

    override fun propertyChange(evt: PropertyChangeEvent?) {
        logger.debug("property change")
        if (evt?.propertyName == IGasStationModel.DATATYPE_STATIONS) {
            val gasStationList = evt.newValue as List<GasStation>
            gasStationListModel.clear()
            gasStationList.forEach { gasStationListModel.addElement(it) }
        }
    }
}





