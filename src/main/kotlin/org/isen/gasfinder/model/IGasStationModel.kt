package org.isen.gasfinder.model

import java.beans.PropertyChangeListener

interface IGasStationModel {
    companion object{
        const val DATATYPE_STATIONS = "stations"
        const val DATATYPE_STATION_SELECTED = "stationSelected"
        const val DATATYPE_SEARCH = "search"
    }
    enum class DataSources (val urlStart: String, val urlEnd: String, val dataType: String) {
        DATAECO("https://data.economie.gouv.fr/api/records/1.0/search/?dataset=prix-carburants-fichier-instantane-test-ods-copie&q=", "&rows=-1&facet=id&facet=cp&facet=pop&facet=adresse&facet=ville&facet=geom&facet=prix_id&facet=prix_valeur&facet=prix_nom&facet=services_service", "JSON"),
        PRIXCARBURANT("https://donnees.roulez-eco.fr/opendata/instantane", "", "XML")
    }

    fun register(datatype:String?,listener: PropertyChangeListener)
    fun unregister(listener:PropertyChangeListener)
    fun changeCurrentSelection(id:String?)
    fun searchGasStations(searchParameters : SearchParameters?, source: DataSources)
}