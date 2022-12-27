package org.isen.gasfinder



import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.model.GasStationInformation
import kotlin.test.*

class GasStationInformationJUnitTest {
    companion object : Logging

    @Test
    fun getFirstStationInformationFromJson() {
        val (request, response, result) = "https://data.economie.gouv.fr/explore/dataset/prix-carburants-fichier-instantane-test-ods-copie/download/?format=json&timezone=Europe/Berlin&lang=fr"
                .httpGet().responseObject(GasStationInformation.Deserializer())

        assertTrue(response.isSuccessful)
        val (si, error) = result
        assertNotNull(si)
        assertEquals("59670001", si.data.gasStations[0].id)
        logger.info("id good")
        assertEquals("59670", si.data.gasStations[0].cp)
        logger.info("id good")
        assertEquals("R", si.data.gasStations[0].pop)
        logger.info("id good")
        assertEquals("769 avenue Albert Mahieu", si.data.gasStations[0].adresse)
        logger.info("id good")

    }

}