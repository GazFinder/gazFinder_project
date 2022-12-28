package org.isen.gasfinder

import org.json.JSONArray
import java.net.URL

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(App().greeting)

    val url = "https://data.economie.gouv.fr/explore/dataset/prix-carburants-fichier-instantane-test-ods-copie/download/?format=json&timezone=Europe/Berlin&lang=fr"
    val jsonData = URL(url).readText()
    val records = JSONArray(jsonData)
    val firstRecord = records.getJSONObject(0)
    val fields = firstRecord.getJSONObject("fields")
    println(fields)
}
