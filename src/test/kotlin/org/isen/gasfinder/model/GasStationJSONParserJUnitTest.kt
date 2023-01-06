package org.isen.gasfinder.model



import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.model.parser.parseGasStationJSON
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