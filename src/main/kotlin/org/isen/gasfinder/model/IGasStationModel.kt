package org.isen.gasfinder.model

import java.beans.PropertyChangeListener

interface IGasStationModel {
    companion object{
        const val DATATYPE_STATION = "station"
        const val DATATYPE_CARTO = "carto"
    }
    enum class DataSources (val url: String, val dataType: String) {
        DATAECO("https://data.economie.gouv.fr/api/records/1.0/search/?dataset=prix-carburants-fichier-instantane-test-ods-copie&q=&rows=10&facet=id&facet=cp&facet=pop&facet=adresse&facet=ville&facet=geom&facet=prix_id&facet=prix_valeur&facet=prix_nom&facet=services_service", "JSON"),
        PRIXCARBURANT("https://donnees.roulez-eco.fr/opendata/instantane","XML")
    }

    fun register(datatype:String?,listener: PropertyChangeListener)
    fun unregister(listener:PropertyChangeListener)
    fun findGasStationInformation(source : DataSources)
    fun changeCurrentSelection(id:String?)
    fun searchGasStations(searchTerm: String)
}