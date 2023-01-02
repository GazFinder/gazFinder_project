package org.isen.gasfinder.model

import com.github.kittinunf.fuel.httpGet
import org.apache.logging.log4j.kotlin.Logging
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates

class GasStationModel: IGasStationModel {


    companion object : Logging

    private  val pcs = PropertyChangeSupport(this)


    private var gasStationInformation: GasStation? by Delegates.observable(null) {
        _, oldValue, newValue ->
        logger.info("gasStationInformation updated")
        pcs.firePropertyChange(IGasStationModel.DATATYPE_STATION, oldValue, newValue)
    }

    private var gasStations = listOf<Record>()

    private var selectedStation:Record? by Delegates.observable(null) {
            _, oldValue, newValue ->
        logger.info("update selectedStation $newValue")
        pcs.firePropertyChange(IGasStationModel.DATATYPE_STATION, oldValue, newValue)
    }

 override fun changeCurrentSelection(Id: String) {
        if (gasStations.isEmpty()) {
            "https://data.economie.gouv.fr/api/records/1.0/search/?dataset=prix-carburants-fichier-instantane-test-ods-copie&q=&rows=10&facet=id&facet=cp&facet=pop&facet=adresse&facet=ville&facet=geom&facet=prix_id&facet=prix_valeur&facet=prix_nom&facet=services_service".httpGet()
                .responseObject(GasStation.Deserializer()) { request, response, result ->
                    logger.info("StatusCode : ${response.statusCode}")
                    result.let { (data, error) ->
                        gasStations = data?.records ?: listOf()
                        selectedStation = gasStations.find {
                           it.fields.id == Id
                        }

                    }
                }
        } else {
            selectedStation = gasStations.find {
                it.fields.id == Id
            }

        }
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
        "https://data.economie.gouv.fr/api/records/1.0/search/?dataset=prix-carburants-fichier-instantane-test-ods-copie&q=&rows=10&facet=id&facet=cp&facet=pop&facet=adresse&facet=ville&facet=geom&facet=prix_id&facet=prix_valeur&facet=prix_nom&facet=services_service"
                .httpGet().responseObject(GasStation.Deserializer()) { request, response, result ->
                    logger.info("StatusCode: ${response.statusCode}")
                    result.let { (data,error) -> gasStationInformation = data }
                }

    }



    var selectedItinerary: Itinerary? = null





    fun sortGasStationsByPrice() {
        //gasStations.sortBy { it.pricePerGallon }
    }



  //  fun searchGasStations(searchTerm: String): List<GasStation> {
       // return gasStations.filter { it.services.contains(searchTerm) }
   // }



}