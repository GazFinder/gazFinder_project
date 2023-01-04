package org.isen.gasfinder.model

import org.apache.logging.log4j.kotlin.Logging
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates

class GasStationModel: IGasStationModel {

    companion object : Logging

    private val pcs = PropertyChangeSupport(this)

    private var searchResultGasStations: List<GasStation> by Delegates.observable(emptyList()) { _, _, _ ->
        logger.info("update search result gas stations")
        pcs.firePropertyChange(IGasStationModel.DATATYPE_STATIONS, null, searchResultGasStations)
    }

    private var selectedStation:GasStation? by Delegates.observable(null) {
            _, oldValue, newValue ->
        logger.info("update selectedStation")
        pcs.firePropertyChange(IGasStationModel.DATATYPE_STATION_SELECTED, oldValue, newValue)
    }

    private var search: StationSearch? by Delegates.observable(null) { _, _, _ ->
        logger.info("update search")
        pcs.firePropertyChange(IGasStationModel.DATATYPE_SEARCH, null, search)
    }




    override fun changeCurrentSelection(id: String?) {
        logger.info("changeCurrentSelection $id")
        selectedStation = searchResultGasStations.find { it.id == id }
    }

    override fun register(datatype: String?, listener: PropertyChangeListener) {
        if(datatype == null) {
            pcs.addPropertyChangeListener(IGasStationModel.DATATYPE_STATIONS, listener)
            pcs.addPropertyChangeListener(IGasStationModel.DATATYPE_STATION_SELECTED, listener)
            pcs.addPropertyChangeListener(IGasStationModel.DATATYPE_SEARCH, listener)
            //pcs.addPropertyChangeListener(IGasStationModel.DATATYPE_CARTO, listener)
        } else {
            pcs.addPropertyChangeListener(datatype, listener)
        }

    }

    override fun unregister(listener: PropertyChangeListener) {
    }

    var selectedItinerary: Itinerary? = null

    fun sortGasStationsByPrice() {
        //gasStations.sortBy { it.pricePerGallon }
    }

  override fun searchGasStations(searchParameters : SearchParameters?, source: IGasStationModel.DataSources) {
      if(searchParameters == null) {
          if(search == null)
              throw IllegalArgumentException("searchParameters is null and no previous search was done")
      } else search = StationSearch(searchParameters)
      search!!.executeSearch(this, source)
      searchResultGasStations = search!!.stations!!
   }
}