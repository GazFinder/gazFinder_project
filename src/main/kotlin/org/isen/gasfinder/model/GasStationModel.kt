package org.isen.gasfinder.model

import com.github.kittinunf.fuel.httpGet
import org.apache.logging.log4j.kotlin.Logging
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates

class GasStationModel: IGasStationModel {


    private val gasStations = mutableListOf<GasStation>()

    companion object : Logging

    private  val pcs = PropertyChangeSupport(this)


    private var gasStationInformation: GasStationInformation? by Delegates.observable(null) {
        _, oldValue, newValue ->
        logger.info("gasStationInformation updated")
        pcs.firePropertyChange(IGasStationModel.DATATYPE_STATION, oldValue, newValue)
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

    override fun findGasStationInformation() {
        "https://data.economie.gouv.fr/explore/dataset/prix-carburants-fichier-instantane-test-ods-copie/download/?format=json&timezone=Europe/Berlin&lang=fr"
                .httpGet().responseObject(GasStationInformation.Deserializer()) { request, response, result ->
                    logger.info("StatusCode: ${response.statusCode}")
                    result.let { (data,error) -> gasStationInformation = data }
                }

    }

    override fun changeCurrentSelection(id: Long) {
        TODO("Not yet implemented")
    }


    var selectedItinerary: Itinerary? = null

    fun addGasStation(gasStation: GasStation) {
        gasStations.add(gasStation)
    }

    fun removeGasStation(gasStation: GasStation) {
        gasStations.remove(gasStation)
    }

    fun sortGasStationsByPrice() {
        //gasStations.sortBy { it.pricePerGallon }
    }

    fun getGasStations(): List<GasStation> {
        return gasStations
    }

  //  fun searchGasStations(searchTerm: String): List<GasStation> {
       // return gasStations.filter { it.services.contains(searchTerm) }
   // }

    fun getGasStationsAlongItinerary(): List<GasStation> {
        val itinerary = selectedItinerary ?: return emptyList()
        //return gasStations.filter { it.location in itinerary }
        return emptyList()
    }


}