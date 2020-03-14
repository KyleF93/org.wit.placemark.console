package org.wit.placemark.console.models

interface PlacemarkStore {
    fun findAll(): List<PlacemarkModel>
    fun findOne(id: Long): PlacemarkModel?
    fun create(placemark: PlacemarkModel)
    fun update(placemark: PlacemarkModel)
}