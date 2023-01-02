package org.isen.gasfinder.view

import org.isen.gasfinder.model.Record
import java.awt.Color
import java.awt.Component
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

class StationInfoCellRender:JLabel(), ListCellRenderer<Record> {

    init {
        super.setOpaque(true)
    }

    override fun getListCellRendererComponent(
        list: JList<out Record> ?,
        value: Record?,
        index:Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ):Component {
    if(isSelected) {
        background = Color.ORANGE
        foreground = list?.foreground
    } else {
        background = list?.background
        foreground = list?.foreground
    }

       /* val img = ImageIcon(this::class.java.getResource(("/tag.png"))).let {
            ImageIcon(it.image.getScaledInstance(5,5, Image.SCALE_DEFAULT))
        }*/


        if (value != null) text = "${value.fields.id} - ${value.fields.adresse} "
        return this
    }

}