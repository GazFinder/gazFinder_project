package org.isen.gasfinder.model

import java.beans.PropertyChangeListener

interface IGasStationModel {
    companion object{
        const val DATATYPE_STATION = "station"
        const val DATATYPE_CARTO = "carto"
    }
    fun register(datatype:String?,listener: PropertyChangeListener)
    fun unregister(listener:PropertyChangeListener)
    fun findGasStationInformation()
    fun changeCurrentSelection(id:String)
}