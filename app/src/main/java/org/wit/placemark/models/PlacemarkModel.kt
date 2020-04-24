package org.wit.placemark.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

interface PlacemarkStore {
    fun findAll(): List<PlacemarkModel>
    fun create(placemark: PlacemarkModel)
    fun update(placemark: PlacemarkModel)
    fun delete(placemark: PlacemarkModel)
}

@Parcelize
data class PlacemarkModel(var id: Long = 0,
                          var description: String = "",
                          var title: String = "",
                          var image: String = "",
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var fbid: String = "",
                          var zoom: Float = 0f) : Parcelable {
    /*@Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "description" to description,
            "id" to id,
            "image" to image,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom
        )
    }*/
}

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

