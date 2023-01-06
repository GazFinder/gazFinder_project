package org.isen.gasfinder.view

import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.controller.GasStationController
import org.isen.gasfinder.model.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import javax.swing.*
import javax.swing.border.Border

//represent a 400*800 small window in which is selected the filters and parameters of the search
class GasStationSearchView(private val controller: GasStationController):IGasStationView, ActionListener  {

    companion object : Logging

    private val frame :JFrame
    private var search: SearchParameters = SearchParameters()
    private val searchField = JTextField(20)
    private val priceField = JTextField(1)

    init {
        controller.registerView(this, listOf(IGasStationModel.DATATYPE_SEARCH))
        frame = JFrame("SearchView").apply {
            isVisible = false
            contentPane = makeGUI()
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            this.title = title
            this.preferredSize = Dimension(700, 600)
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
        if(evt?.propertyName == IGasStationModel.DATATYPE_SEARCH) {
            val search = evt.newValue as StationSearch
            if(search.search != null){
                this.search = search.search!!
                logger.info("Search parameters updated: $search")
            }
        }
    }

    private fun makeGUI(): JPanel {
        val result = JPanel()
        result.layout = BorderLayout()
        result.add(makeSearchBar(), BorderLayout.NORTH)
        result.add(makeContentPanel(), BorderLayout.CENTER)
        result.add(makeSearchButton(), BorderLayout.SOUTH)
        return result
    }

    private fun makeContentPanel(): JPanel {
        val result = JPanel()
        result.layout = BoxLayout(result,BoxLayout.X_AXIS)
        result.add(makeScrollablePanel())
        result.add(makeFuelPanel())
        result.background = Color.WHITE
        return result
    }
    private fun makeSearchBar(): JPanel {
        val panel = JPanel()
        panel.layout = BoxLayout(panel,BoxLayout.Y_AXIS)
        panel.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
        val lineBorder: Border = BorderFactory.createTitledBorder("Recherche")
        searchField.border = lineBorder
        panel.add(searchField)
        panel.background = Color.WHITE
        return panel
    }

    private fun makeScrollablePanel() : JPanel{
        val boxPanel = JPanel()
        boxPanel.layout = BoxLayout(boxPanel,BoxLayout.Y_AXIS)
        boxPanel.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
        val checkboxPanel = JPanel()
        checkboxPanel.layout = BoxLayout(checkboxPanel,BoxLayout.Y_AXIS);
        checkboxPanel.background = Color.WHITE
        // Add some checkboxes to the panel
        for (service in GasStation.Service.values()) {
            if(service != GasStation.Service.UNKNOWN) {
                val checkbox = JCheckBox(service.value);
                checkbox.background = Color.WHITE
                checkbox.addActionListener{
                    if(search.services == null) {
                        search.services = mutableListOf()
                    }
                    if(checkbox.isSelected) {
                        search.services?.add(service)
                    } else {
                        search.services?.remove(service)
                    }
                }
                checkboxPanel.add(checkbox)
            }
        }
        val scrollPane = JScrollPane(checkboxPanel)
        scrollPane.preferredSize = Dimension(200, 200)
        scrollPane.maximumSize = Dimension(500, 1000)
        scrollPane.border = BorderFactory.createTitledBorder("Services")

        // Set the size of the scroll pane
        scrollPane.background = Color.WHITE
        boxPanel.add(scrollPane)
        boxPanel.background = Color.WHITE
        return boxPanel
    }

    private fun makeFuelPanel() : JPanel{
        val boxPanel = JPanel()
        boxPanel.layout = BoxLayout(boxPanel,BoxLayout.Y_AXIS)
        boxPanel.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
        boxPanel.background = Color.WHITE
        boxPanel.minimumSize = Dimension(400, 300)
        boxPanel.maximumSize = Dimension(400,300)

        //Contain a combobox to select a fuel and a text field to select a max price
        val gasContainerPanel = JPanel()
        gasContainerPanel.border = BorderFactory.createTitledBorder("Carburant")
        gasContainerPanel.layout = BoxLayout(gasContainerPanel,BoxLayout.Y_AXIS)
        gasContainerPanel.background = Color.WHITE
        val gasComboBox = JComboBox<String>()
        gasComboBox.minimumSize = Dimension(400, 100)
        gasComboBox.maximumSize = Dimension(400, 100)
        gasComboBox.background = Color.WHITE
        gasComboBox.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
        gasComboBox.addItem("Aucun")
        for (fuel in GasStation.Gas.GasType.values()){
            gasComboBox.addItem(fuel.name)
        }
        gasComboBox.addActionListener {
            if(gasComboBox.selectedItem == "Aucun"){
                search.gas = null
            } else {
                search.gas = GasStation.Gas.GasType.valueOf(gasComboBox.selectedItem as String)
            }
        }
        gasContainerPanel.add(gasComboBox)
        val priceContainerPanel = JPanel()
        priceContainerPanel.layout = BoxLayout(priceContainerPanel,BoxLayout.X_AXIS)
        priceContainerPanel.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
        priceContainerPanel.background = Color.WHITE
        priceContainerPanel.minimumSize = Dimension(400, 50)
        priceContainerPanel.maximumSize = Dimension(400, 50)
        priceField.preferredSize = Dimension(400, 50)
        priceField.minimumSize = Dimension(400, 50)
        priceField.maximumSize = Dimension(400, 50)
        priceField.border = BorderFactory.createTitledBorder("Prix max")
        priceContainerPanel.add(priceField)
        gasContainerPanel.add(priceContainerPanel)
        boxPanel.add(gasContainerPanel)

        //Data source selection
        val dataSourcePanel = JPanel()
        dataSourcePanel.border = BorderFactory.createTitledBorder("Source de données")
        dataSourcePanel.layout = FlowLayout()
        dataSourcePanel.background = Color.WHITE
        val dataSourceComboBox = JComboBox<String>()
        dataSourceComboBox.background = Color.WHITE
        for(source in IGasStationModel.DataSources.values()){
            dataSourceComboBox.addItem(source.name)
        }
        dataSourceComboBox.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
        dataSourceComboBox.addActionListener {
            controller.source = IGasStationModel.DataSources.valueOf(dataSourceComboBox.selectedItem as String)
        }
        dataSourcePanel.add(dataSourceComboBox)
        boxPanel.add(dataSourcePanel)
        return boxPanel
    }

    private fun makeSearchButton() : JPanel{
        val panel = JPanel()
        panel.layout = BoxLayout(panel,BoxLayout.Y_AXIS)
        panel.border = BorderFactory.createEmptyBorder(10, 30, 10, 30)
        val button = JButton("Rechercher")
        button.addActionListener{
            search.searchStr = searchField.text
            if(priceField.text == ""){
                search.maxGasPrice = null
            } else {
                search.maxGasPrice = priceField.text.replace(",",".").replace("€","").toDouble()
            }
            controller.handleSearch(search)
        }
        panel.add(button)
        panel.background = Color.WHITE
        return panel
    }

    override fun actionPerformed(e: ActionEvent?) {

    }
}