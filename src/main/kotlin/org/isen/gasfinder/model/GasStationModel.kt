package org.isen.gasfinder.model

import org.apache.logging.log4j.kotlin.Logging
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates

class GasStationModel: IGasStationModel {


    companion object : Logging

    private val pcs = PropertyChangeSupport(this)

    private var gasStations: List<GasStation> by Delegates.observable(emptyList()) { _, _, _ ->
        pcs.firePropertyChange(IGasStationModel.DATATYPE_STATION, null, gasStations)
    }

    private var selectedStation:GasStation? by Delegates.observable(null) {
            _, oldValue, newValue ->
        logger.info("update selectedStation $newValue")
        pcs.firePropertyChange(IGasStationModel.DATATYPE_STATION, oldValue, newValue)
    }

    override fun changeCurrentSelection(id: String?) {
        logger.info("changeCurrentSelection $id")
        selectedStation = gasStations.find { it.id == id }
    }

    override fun register(datatype: String?, listener: PropertyChangeListener) {
        if(datatype == null) {
            pcs.addPropertyChangeListener(IGasStationModel.DATATYPE_STATION, listener)
            //pcs.addPropertyChangeListener(IGasStationModel.DATATYPE_CARTO, listener)
        } else {
            pcs.addPropertyChangeListener(datatype, listener)
        }

    }

    override fun unregister(listener: PropertyChangeListener) {
    }

    override fun findGasStationInformation(source: IGasStationModel.DataSources) {
        logger.info("findGasStationInformation")
        when(source){
            IGasStationModel.DataSources.DATAECO -> {
                gasStations = parseGasStationJSON(source.url)
                println(gasStations.joinToString("\n"))
            }
            IGasStationModel.DataSources.PRIXCARBURANT -> {
            }
        }
    }

   // override fun changeCurrentSelection(id: String) {
     //
    //}

    var selectedItinerary: Itinerary? = null

    fun sortGasStationsByPrice() {
        //gasStations.sortBy { it.pricePerGallon }
    }

  //  fun searchGasStations(searchTerm: String): List<GasStation> {
       // return gasStations.filter { it.services.contains(searchTerm) }
   // }
}