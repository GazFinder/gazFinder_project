package org.isen.gasfinder.view

import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.IGasStationModel
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.*
import javax.swing.border.Border
import javax.swing.plaf.basic.BasicBorders.MarginBorder

class SearchView(val controller: GasStationController):IGasStationView, ActionListener  {

    companion object : Logging

    private val frame :JFrame


    init {
        controller.registerView(this, listOf(IGasStationModel.DATATYPE_SEARCH))
        frame = JFrame("SearchView").apply {
            isVisible = false
            contentPane = makeGUI()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            this.title = title
            this.preferredSize = Dimension(500, 300)
            this.pack()
        }
    }

    override fun display() {
        frame.isVisible = true
    }

    override fun close() {
        frame.isVisible = false
    }

    override fun propertyChange(evt: PropertyChangeEvent?) {
    }

    private fun makeGUI(): JPanel {
        val result = JPanel()
        result.layout = BoxLayout(result,BoxLayout.Y_AXIS)
        result.add(makeSearchBar())
        result.add(makeScrollablePanel())
        result.add(makeFuelPanel())
        return result
    }

    private fun makeSearchBar(): JPanel {
        val panel = JPanel()
        panel.layout = BoxLayout(panel,BoxLayout.Y_AXIS)
        val searchField = JTextField(20)
        searchField.preferredSize = Dimension(20,30)
        searchField.maximumSize = searchField.preferredSize
        searchField.minimumSize = searchField.preferredSize
        val lineBorder: Border = BorderFactory.createLineBorder(Color.BLACK, 2)
        val margin: Border = BorderFactory.createEmptyBorder(2, 2, 2, 2)
        val border: Border = BorderFactory.createCompoundBorder(lineBorder, margin)
        searchField.border = border
        searchField.foreground = Color.GRAY
        searchField.addActionListener {
            val searchText = searchField.text
            println("Searching for: $searchText")
        }
        val searchText = JLabel()
        searchText.text = "Search for anything"
        panel.add(searchText)
        panel.add(searchField)
        return panel
    }

    fun makeScrollablePanel() : JPanel{
        val boxPanel = JPanel()
        boxPanel.layout = BoxLayout(boxPanel,BoxLayout.Y_AXIS)
        val serviceLabel = JLabel("Services")
        val checkboxPanel = JPanel()
        checkboxPanel.layout = BoxLayout(checkboxPanel,BoxLayout.Y_AXIS);
        checkboxPanel.add(Box.createRigidArea(Dimension(100, 0)));
        boxPanel.add(serviceLabel)
        // Add some checkboxes to the panel
        for (service in GasStation.Service.values()) {
            val checkbox = JCheckBox(service.value);
            checkboxPanel.add(checkbox);
        }
        val scrollPane = JScrollPane(checkboxPanel)

        // Set the size of the scroll pane
        scrollPane.preferredSize = Dimension(200,400)
        boxPanel.add(scrollPane)
        return boxPanel
    }

    private fun makeFuelPanel() : JPanel{
        val boxPanel = JPanel()
        boxPanel.layout = BoxLayout(boxPanel,BoxLayout.Y_AXIS)

        val panel = JPanel()
        panel.layout = FlowLayout()
        panel.add(Box.createRigidArea(Dimension(0,0)));
        panel.preferredSize = Dimension(70,40)
        val items = listOf<String>("SP95","SPG98","Gazole")
        val fuelLabel = JLabel("Essence")
        val comboBox = JComboBox(items.toTypedArray())
        panel.add(fuelLabel)
        panel.add(comboBox)
        boxPanel.add(panel)

        val panel2 = JPanel()
        val priceLabel = JLabel("Prix max")
        panel2.add(priceLabel)
        val priceField = JTextField()
        priceField.preferredSize = Dimension(50,20)
        panel2.add(priceField)
        boxPanel.add(panel2)
        return boxPanel
  }

    override fun actionPerformed(e: ActionEvent?) {

    }
}