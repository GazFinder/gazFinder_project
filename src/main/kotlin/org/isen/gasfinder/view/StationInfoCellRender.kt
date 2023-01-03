package org.isen.gasfinder.view

import org.isen.gasfinder.model.GasStation
import java.awt.Component
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

class StationInfoCellRender:JLabel(), ListCellRenderer<GasStation> {

    init {
        super.setOpaque(true)
    }

    override fun getListCellRendererComponent(
        list: JList<out GasStation>?,
        value: GasStation?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val component = this
        if (value is GasStation) {
            text = "${value.geoPoint.address}\n${value.geoPoint.city}"
            icon = img
        }
        return component
    }

}