package org.isen.gasfinder



import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.apache.logging.log4j.kotlin.Logging
import org.isen.gasfinder.model.GasStation
import kotlin.test.*

class GasStationJUnitTest {
    companion object : Logging

 /*   @Test
    fun getFirstStationInformationFromJson() {
        val (request, response, result) = "https://data.economie.gouv.fr/api/records/1.0/search/?dataset=prix-carburants-fichier-instantane-test-ods-copie&q=&rows=10&facet=id&facet=cp&facet=pop&facet=adresse&facet=ville&facet=geom&facet=prix_id&facet=prix_valeur&facet=prix_nom&facet=services_service"
            .httpGet().responseObject(GasStation.Deserializer())

        println(response.statusCode)
        val (si, error) = result
        assertNotNull(si)
        assertEquals("R", si.records[0].fields.pop)
        println("oui")
        assertEquals("59670001", si.records[0].fields.id)
        println("yes")
        assertEquals("59670", si.records[0].fields.cp)
        logger.info("id good")
        assertEquals("R", si.records[0].fields.pop)*/
//        logger.info("id good")
//        assertEquals("769 avenue Albert Mahieu", si.data.gasStations[0].adresse)
//        logger.info("id good")



    @org.junit.Test
    fun getFirstStationInformationFromJson() {
        val (_, response, result) = "https://data.economie.gouv.fr/api/records/1.0/search/?dataset=prix-carburants-fichier-instantane-test-ods-copie&q=&rows=10&facet=id&facet=cp&facet=pop&facet=adresse&facet=ville&facet=geom&facet=prix_id&facet=prix_valeur&facet=prix_nom&facet=services_service".httpGet()
            .responseObject(GasStation.Deserializer())

        println(result)
        assertTrue(response.isSuccessful)
        val (si, error) = result
        assertNotNull(si)
        println("oui")
        assertEquals("59670001", si.records[0].fields.id)


    }

}