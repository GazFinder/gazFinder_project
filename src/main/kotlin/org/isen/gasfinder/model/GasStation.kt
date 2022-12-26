package org.isen.gasfinder.model

//Import proposé par l'auto-complétion
//--------------v------------------
//import javax.xml.stream.Location

data class GasStation(val location: GeoPoint, val pricePerGallon: Double, val services: List<String>){
}
