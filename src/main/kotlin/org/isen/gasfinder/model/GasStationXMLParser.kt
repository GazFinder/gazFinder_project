package org.isen.gasfinder.model

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.io.InputStream
import java.lang.StringBuilder
import java.net.URL
import java.util.zip.ZipInputStream
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

fun parseGasStationXML(url: String): MutableList<GasStation>{
    val parserFactory: SAXParserFactory = SAXParserFactory.newInstance()
    val saxParser: SAXParser = parserFactory.newSAXParser()
    val list : MutableList<GasStation> = mutableListOf()

    var data = StringBuilder()
    var id: String? = null
    var adresse :String? = null
    var Badresse = false
    var cp: String? = ""
    var latitude = "0.00"
    var longitude = "0.00"
    var pop: String? = "false"
    var isOnHighway : Boolean?
    var prix_id: String?
    var prix_valeur: String?
    var Bville= false
    var ville :String? = null
    var Bservices = false
    var servicesList : MutableList<GasStation.Service> = mutableListOf()
    var gasList : MutableList<GasStation.Gas> = mutableListOf()

    val defaultHandler= object : DefaultHandler() {

        override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
            if (qName.equals("pdv")){
                id = attributes?.getValue("id")
                latitude = attributes!!.getValue("latitude")
                longitude = attributes.getValue("longitude")
                cp = attributes.getValue("cp")
                pop = attributes.getValue("pop")
            }
            else if (qName.equals("adresse")) {
                Badresse = true
            }
            else if (qName.equals("ville")) {
                Bville = true
            }
            else if (qName.equals("service")) {
                Bservices = true
            }
            else if (qName.equals("prix")) {
                prix_id = attributes?.getValue("id")
                prix_valeur = attributes?.getValue("valeur")
                if (prix_id != null && prix_valeur != null){
                    gasList.add(GasStation.Gas(prix_id!!, prix_valeur!!.toDouble()))
                }
            }
            data = StringBuilder()
        }

        override fun endElement(uri: String?, localName: String?, qName: String?) {
            if (Bville){
                ville = data.toString()
                Bville = false
            }
            if (Badresse){
                adresse = data.toString()
                Badresse = false
            }
            if (Bservices){
                servicesList.add(GasStation.getServiceFromString(data.toString()))
                Bservices = false
            }

            if (qName.equals("pdv")){
                val point = GeoPoint(latitude.toDouble()/100000,longitude.toDouble()/100000,adresse.toString(),ville.toString(),cp)
                isOnHighway = pop == "A"
                if (id != null){
                    val station = GasStation(point,null,gasList,servicesList, isOnHighway!!, id!!)
                    list.add(station)
                    servicesList = mutableListOf()
                    gasList = mutableListOf()
                }
            }
        }


        override fun characters(ch: CharArray?, start: Int, length: Int) {
            data.append(String(ch!!, start, length))
            data.trim()
        }
    }


    fun parseXML(url: String): InputStream {
        val connection = URL(url).openConnection()
        val inputStream = connection.getInputStream()
        val contentType = connection.getContentType()
        return if (contentType != null && contentType.equals("application/zip", ignoreCase = true)) {
            val zipInputStream = ZipInputStream(inputStream)
            zipInputStream.nextEntry
            zipInputStream
        } else {
            inputStream
        }
    }

    val inputStream = parseXML(url)
    saxParser.parse(inputStream,defaultHandler)
    return list
}