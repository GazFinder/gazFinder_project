
package org.isen.gasfinder.view



import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.IGasStationModel
import java.awt.BorderLayout
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.*


class GasStationListPanel (private val controller: GasStationController): PropertyChangeListener,
    JPanel() {

    companion object : Logging
    private val gasStationList = JList<GasStation>()
    private val gasStationListModel = DefaultListModel<GasStation>()
    private val gasStationListRenderer = StationInfoCellRender()
    private val gasStationListScrollPane = JScrollPane(gasStationList)

    init {
        controller.model.register(IGasStationModel.DATATYPE_STATIONS, this)
        controller.model.register(IGasStationModel.DATATYPE_STATION_SELECTED, this)

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

        this.layout = BorderLayout(20, 20)
        gasStationList.model = gasStationListModel
        gasStationList.cellRenderer = gasStationListRenderer
        this.add(gasStationListScrollPane, BorderLayout.CENTER)
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





