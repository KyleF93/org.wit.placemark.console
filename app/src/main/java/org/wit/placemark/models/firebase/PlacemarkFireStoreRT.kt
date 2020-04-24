package org.wit.placemark.models.firebase
/*

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.*

class PlacemarkFireStoreRT(val context: Context) : PlacemarkStore, AnkoLogger {

    private val placemarks = ArrayList<PlacemarkModel>()

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    private lateinit var database: DatabaseReference

    override fun findAll(): List<PlacemarkModel> {
        return placemarks
    }

    override fun create(placemark: PlacemarkModel) {
        placemark.id = generateRandomId()
        //var x = placemark.title
        database = Firebase.database.reference
        placemarks.add(placemark)
        val placemarks: List<PlacemarkModel> = mutableListOf(
            PlacemarkModel(placemark.id, placemark.description, placemark.title, placemark.image, placemark.lat,
                placemark.lng, placemark.fbid, placemark.zoom)
        )
        placemarks.forEach {
            val key = database.child("placemarks").push().key
            if (key != null) {
                it.fbid = key
            }
            if (key != null) {
                database.child("placemarks").child(key).setValue(it)
            }
        }
        /*/ Create a new placemark in firestore with the following fields
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
        db.collection("placemarks").document(x)
            .set(placemark)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/
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

            val update = placemark.toMap()
            val key = placemark.fbid

            val childUpdates = HashMap<String, Any>()
            childUpdates["/placemarks"] = update

            database.child("placemarks").updateChildren(childUpdates)

        }
    }
    override fun delete(placemark: PlacemarkModel) {
        placemarks.remove(placemark)
        val key = placemark.fbid
        database.child("placemarks").child(key).removeValue()
        /*var x = placemark.title
        db.collection("placemarks").document(x)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }*/
    }

    fun logAll() {
        placemarks.forEach { info("${it}") }
    }
}*/