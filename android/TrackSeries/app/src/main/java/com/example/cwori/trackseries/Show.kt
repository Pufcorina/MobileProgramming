package com.example.cwori.trackseries

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Show(
    @PrimaryKey var id: String? = "0",
    var title: String? = "title",
    var producer: String? = "producer",
    var rating: Int? = 0,
    var description: String? = "description"
): RealmObject() {

}