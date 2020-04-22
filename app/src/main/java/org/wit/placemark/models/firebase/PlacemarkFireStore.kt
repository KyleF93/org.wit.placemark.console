package org.wit.placemark.models.firebase


import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.*

class PlacemarkFireStore(val context: Context) : PlacemarkStore, AnkoLogger {

    val placemarks = ArrayList<PlacemarkModel>()

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun findAll(): List<PlacemarkModel> {
        return placemarks
    }

    override fun create(placemark: PlacemarkModel) {
        placemark.id = generateRandomId()
        placemarks.add(placemark)
        // Create a new placemark in firestore with the following fields
        val placemark = hashMapOf(
            "title" to placemark.title,
            "description" to placemark.description,
            "id" to placemark.id,
            "image" to placemark.image,
            "lat" to placemark.lat,
            "lng" to placemark.lng,
            "zoom" to placemark.zoom
        )

        // Add a new document with a generated ID
        db.collection("placemarks")
            .add(placemark)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }


    override fun update(placemark: PlacemarkModel) {

        var foundPlacemark: PlacemarkModel? = placemarks.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.description = placemark.description
            foundPlacemark.image = placemark.image
            foundPlacemark.lat = placemark.lat
            foundPlacemark.lng = placemark.lng
            foundPlacemark.zoom = placemark.zoom
            logAll()
        }
    }
    override fun delete(placemark: PlacemarkModel) {
        placemarks.remove(placemark)
    }

    fun logAll() {
        placemarks.forEach { info("${it}") }
    }
}