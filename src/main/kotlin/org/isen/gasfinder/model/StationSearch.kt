package org.isen.gasfinder.model

import org.isen.gasfinder.model.parser.parseGasStationJSON
import org.isen.gasfinder.model.parser.parseGasStationXML


data class SearchParameters(
    var searchStr: String? = null,
    val maxDistance: Int? = null,
    var services: MutableList<GasStation.Service>? = null,
    var gas: GasStation.Gas.GasType? = null,
    var maxGasPrice: Double? = null,
)

class StationSearch (var search: SearchParameters? = null) {
    private var hasBeenSearched = false
    var stations: List<GasStation>? = null

    init {
        if (search == null) {
            search = SearchParameters()
        }
    }

    fun executeSearch(gasStationModel: IGasStationModel, source: IGasStationModel.DataSources) {
        GasStationModel.logger.info("findGasStationInformation $source")

        println("new search : $search")

        if(search == null) {
            throw IllegalArgumentException("searchParameters is null and no previous search was done")
            return
        }

        this.stations = when(source){
            IGasStationModel.DataSources.DATAECO -> parseGasStationJSON(source.urlStart + search!!.searchStr + source.urlEnd)
            IGasStationModel.DataSources.PRIXCARBURANT -> parseGasStationXML(source.urlStart)
        }

        GasStationModel.logger.info("findGasStationInformation $source done, ${stations?.size} stations found")
        println("findGasStationInformation $source done, ${stations?.size} stations found")

        if(stations?.size == 0) {
            val alternativeSource =
                if (source == IGasStationModel.DataSources.DATAECO) IGasStationModel.DataSources.PRIXCARBURANT else IGasStationModel.DataSources.DATAECO
            GasStationModel.logger.info("No station found, retrying with $alternativeSource")
            println("No station found, retrying with $alternativeSource")
            stations = when (alternativeSource) {
                IGasStationModel.DataSources.DATAECO -> parseGasStationJSON(alternativeSource.urlStart + search!!.searchStr + alternativeSource.urlEnd)
                IGasStationModel.DataSources.PRIXCARBURANT -> parseGasStationXML(alternativeSource.urlStart)
            }
            GasStationModel.logger.info("findGasStationInformation $alternativeSource done, ${stations?.size} stations found")
            println("findGasStationInformation $alternativeSource done, ${stations?.size} stations found")
        }

        val searchWords = search!!.searchStr?.split(" ")
        val searchWordsWithAttributes = searchWords?.map {
            var isWordACity = false
            var isWordAnAddress = false
            var isWordAPostalCode = false
            var isWordAService = false
            var isWordAGasType = false

            stations?.forEach { station ->
                if(station.geoPoint.city.equals(it, true)) isWordACity = true
                if(station.geoPoint.address.equals(it, true)) isWordAnAddress = true
                if(station.geoPoint.postalCode.equals(it, true)) isWordAPostalCode = true
                if(GasStation.getServiceFromString(it) != GasStation.Service.UNKNOWN) isWordAService = true
                if(GasStation.Gas.GasType.values().any { gasType -> gasType.name == it }) isWordAGasType = true
            }

            val attribute =
                if(isWordACity) "city"
                else if(isWordAnAddress) "address"
                else if(isWordAPostalCode) "postalCode"
                else if(isWordAService) "service"
                else if(isWordAGasType) "gasType"
                else "unknown"
            listOf(it, attribute)
        }

        stations = stations?.filter { station ->
            var isOk = true
            if(search!!.services != null) {
                isOk = isOk && search!!.services?.let { station.services.containsAll(it) } == true
            }
            if(search!!.gas != null) {
                isOk = isOk && station.gas.any { it.type == search!!.gas }
            }
            if(search!!.maxGasPrice != null) {
                isOk = isOk && station.gas.any { it.price <= search!!.maxGasPrice!! }
            }

            searchWordsWithAttributes?.forEach { searchWordWithAttribute ->
                if(searchWordWithAttribute[1] == "city") isOk = isOk && station.geoPoint.city.equals(searchWordWithAttribute[0], ignoreCase = true)
                if(searchWordWithAttribute[1] == "address") isOk = isOk && station.geoPoint.address?.contains(searchWordWithAttribute[0], true) ?: false
                if(searchWordWithAttribute[1] == "postalCode") isOk = isOk && station.geoPoint.postalCode?.contains(searchWordWithAttribute[0], true) ?: false
                if(searchWordWithAttribute[1] == "service") isOk = isOk && station.services.any { it.name.contains(searchWordWithAttribute[0], true) }
                isOk = if(searchWordWithAttribute[1] == "gasType") isOk && station.gas.any { it.type.name.contains(searchWordWithAttribute[0], true) }
                else isOk && station.getSearchableString().contains(searchWordWithAttribute[0], true)
            }

            if(search!!.maxDistance != null) {
                isOk = isOk && station.distance != null && station.distance!! <= search!!.maxDistance!!
            }
            isOk
        }

        //remove stations if there is more than 100, based on the proximity to the search


        if((stations?.size ?: 0) > 50) {
            stations = stations?.sortedBy { it.distance }
            stations = stations?.subList(0, 50)
        }

        println("After filtering, ${stations?.size} stations found")
        hasBeenSearched = true
    }
}