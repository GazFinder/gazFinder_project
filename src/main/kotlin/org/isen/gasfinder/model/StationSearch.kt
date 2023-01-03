package org.isen.gasfinder.model

data class SearchParameters(
    val searchStr: String? = null,
    val maxDistance: Int? = null,
    val services: List<GasStation.Service>? = null,
    val gas: GasStation.Gas.GasType? = null,
    val maxGasPrice: Double? = null,
)

class StationSearch (val search: SearchParameters){
    var hasBeenSearched = false
    var stations: List<GasStation>? = null

    fun executeSearch(gasStationModel: IGasStationModel, source: IGasStationModel.DataSources) {
        GasStationModel.logger.info("findGasStationInformation $source")

        when(source){
            IGasStationModel.DataSources.DATAECO -> {
                stations = parseGasStationJSON(source.urlStart + search.searchStr + source.urlEnd)
            }
            IGasStationModel.DataSources.PRIXCARBURANT -> {
            }
        }

        GasStationModel.logger.info("findGasStationInformation $source done, ${stations?.size} stations found")
        println("findGasStationInformation $source done, ${stations?.size} stations found")

        stations = stations?.filter { station ->
            var isOk = true
            if(search.services != null) {
                isOk = isOk && station.services.containsAll(search.services)
            }
            if(search.gas != null) {
                isOk = isOk && station.gas.any { it.type == search.gas }
            }
            if(search.maxGasPrice != null) {
                isOk = isOk && station.gas.any { it.price <= search.maxGasPrice }
            }
            if(search.searchStr != null) {
                //isOk = isOk && station.getSearchableString().contains(search.searchStr, true)
            }
            if(search.maxDistance != null) {
                isOk = isOk && station.distance != null && station.distance!! <= search.maxDistance
            }
            isOk
        }

        println("After filtering, ${stations?.size} stations found")
        hasBeenSearched = true
    }
}