package org.isen.gasfinder.view

import org.isen.gasfinder.model.GasStation
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer
import java.awt.Image
import javax.swing.ImageIcon

class StationInfoCellRender:JLabel(), ListCellRenderer<GasStation> {

    init {
        super.setOpaque(true)
    }

    val img = ImageIcon(this::class.java.getResource(("/station_icon.png"))).let {
        ImageIcon(it.image.getScaledInstance(50, 50, Image.SCALE_DEFAULT))
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
            text = "${value.geoPoint.address} - ${value.geoPoint.city}"
            icon = img
        }
        return component
    }

}

