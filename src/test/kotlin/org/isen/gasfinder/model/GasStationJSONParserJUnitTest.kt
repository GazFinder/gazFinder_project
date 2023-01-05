package org.isen.gasfinder.model



import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.model.GasStation
import org.isen.gasfinder.model.GasStationJSONParser
import org.isen.gasfinder.model.IGasStationModel
import org.isen.gasfinder.model.parseGasStationJSON
import kotlin.test.*

class GasStationJSONParserJUnitTest {
    companion object : Logging

    @org.junit.Test
    fun getFirstStationInformationFromJson() {
        val source = IGasStationModel.DataSources.DATAECO
        val url = source.urlStart + source.urlEnd
        val list : List<GasStation> = parseGasStationJSON(url)
        assertFalse(list.isEmpty(),"No stations detected from the JSON Parser")
        assertTrue(list.size > 1000, "Missing stations from JSON Parser Result")
    }

}