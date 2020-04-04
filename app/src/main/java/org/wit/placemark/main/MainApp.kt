package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.PlacemarkMemStore
import org.wit.placemark.models.PlacemarkModel

class MainApp : Application(), AnkoLogger {

  val placemarks = PlacemarkMemStore()

  override fun onCreate() {
    super.onCreate()
    info("Placemark started")
  }
}