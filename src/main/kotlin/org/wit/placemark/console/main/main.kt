package org.wit.placemark.console.main

import mu.KotlinLogging
import org.wit.placemark.console.controller.PlacemarkController

private val logger = KotlinLogging.logger {}
val controller = PlacemarkController()


fun main() {
    logger.info { "Launching Placemark Console App" }
    println("Placemark Kotlin App Version 1.0")

    var input: Int

    do {
        input = controller.menu()
        when(input) {
            1 -> addPlacemark()
            2 -> updatePlacemark()
            3 -> listPlacemarks()
            4 -> searchPlacemark()
            -99 -> dummyData()
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)
    logger.info { "Shutting Down Placemark Console App" }
}

fun addPlacemark(){
    controller.add()
}


fun updatePlacemark() {
    controller.update()
}

fun listPlacemarks() {
    controller.list()
}

fun searchPlacemark() {
    controller.search()
}

fun dummyData() {
    controller.dummyData()
}

