package org.isen.gasfinder.view


import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStation

import java.awt.BorderLayout
import java.awt.Dimension
import java.beans.PropertyChangeEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.WindowConstants

class TestView(val controller: GasStationController):IGasStationView {
    companion object : Logging

    private val frame: JFrame
    private val myText = JTextField()

    init {
        controller.registerViewToGasData(this)
        frame = JFrame("TestView").apply {
            isVisible = false
            contentPane = makeGUI()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            this.title = title
            this.preferredSize = Dimension(500, 300)
            this.pack()
        }
    }

    private fun makeGUI(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout()
        result.add(makeNorthArea(), BorderLayout.NORTH)
        return result
    }

    private fun makeNorthArea(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout()
        result.add(myText, BorderLayout.CENTER)
        val mybutton = JButton("get Data")
        mybutton.addActionListener() {
            logger.debug("call controller")
            controller.loadGasStationInformation()
        }
        result.add(mybutton, BorderLayout.EAST)
        result.add(JLabel("Station :"), BorderLayout.WEST)
        return result
    }

    override fun display() {
        frame.isVisible = true
    }

    override fun close() {
    }

    override fun propertyChange(evt: PropertyChangeEvent) {
        if (evt.newValue is GasStation) {
            (evt.newValue as GasStation).let {
                if (it.records.isNotEmpty()) {
                    /*  val firstStation = it.records[0]
                      logger.info("first station is $firstStation")*/
                    myText.text = "(${it.records[0].fields.id}) - ${it.records[0].fields.ville}"
                } else {
                    logger.warn("unknown information")
                }
            }
        }
    }
}