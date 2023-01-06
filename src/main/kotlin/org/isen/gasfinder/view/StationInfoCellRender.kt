package org.isen.gasfinder.view

import org.isen.gasfinder.model.GasStation
import java.awt.*
import javax.swing.*

class StationInfoCellRender:JLabel(), ListCellRenderer<GasStation> {

    init {
        super.setOpaque(true)
    }

    companion object {
        private val stationIcon = ImageIcon(this::class.java.getResource(("/station_icon.png"))).let {
            ImageIcon(it.image.getScaledInstance(50, 50, Image.SCALE_DEFAULT))
        }

        private val gasIcons : MutableList<Triple<ImageIcon, GasStation.Gas.GasType, String>> = mutableListOf()
        init {
            GasStation.Gas.GasType.values().forEach { it ->
                val imageName = GasStation.Gas.getGasIconNameFromType(it)
                val image = ImageIcon(ImageIcon(this::class.java.getResource(("/icons/$imageName"))).image.getScaledInstance(32, 32, Image.SCALE_DEFAULT))
                gasIcons.add(Triple(image, it, imageName))
            }
        }
    }

    override fun getListCellRendererComponent(
        list: JList<out GasStation>?,
        value: GasStation?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if(isSelected) {
            background = list?.selectionBackground
            foreground = list?.selectionForeground
        } else {
            background = list?.background
            foreground = list?.foreground
        }
        val component = this
        component.layout = BorderLayout()
        component.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

        if (value is GasStation) {
            val stationGasIcons = mutableListOf<Triple<ImageIcon, GasStation.Gas.GasType, String>>()
            value.gas.forEach {
                val gasIconName = GasStation.Gas.getGasIconNameFromType(it.type)
                val gasIcon = gasIcons.first { icon -> icon.third == gasIconName }
                if(stationGasIcons.none { icon -> icon.third == gasIconName }) {
                    stationGasIcons.add(gasIcon)
                }
            }
            text = "<HTML><h3>${value.geoPoint.address}</h3><p>${value.geoPoint.city?.uppercase()}</p><br><br></HTML>"
            icon = stationIcon
            val panel = JPanel(FlowLayout(FlowLayout.RIGHT))
            panel.background = Color(0, 0, 0, 0)
            stationGasIcons.forEach { panel.add(JLabel(it.first)) }
            component.removeAll()
            component.add(panel, BorderLayout.SOUTH)
        }
        return component
    }
}

