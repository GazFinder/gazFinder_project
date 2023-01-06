package org.isen.gasfinder.model

import kotlin.test.assertFalse
import kotlin.test.assertTrue


internal class GasStationXMLParserKtTest {

    @org.junit.Test
    fun parseGasStationXML() {
        val source = IGasStationModel.DataSources.PRIXCARBURANT
        val url = source.urlStart
        val list : List<GasStation> = org.isen.gasfinder.model.parser.parseGasStationXML(url)
        assertFalse(list.isEmpty(),"No stations detected from the XML Parser")
        assertTrue(list.size > 1000, "Missing stations from XML Parser Result")
    }
}