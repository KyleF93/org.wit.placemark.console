package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.PlacemarkStore
import org.wit.placemark.models.firebase.PlacemarkFireStore

class MainApp : Application(), AnkoLogger {

    lateinit var placemarks: PlacemarkStore

    override fun onCreate() {
        super.onCreate()
        placemarks = PlacemarkFireStore(applicationContext)
        info("Placemark started")
    }
}