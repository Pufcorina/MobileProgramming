package com.example.marketapp.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ProductRealm : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name = ""
    var description = ""
    var status = ""
    var quantity = 0
    var price = 0

    override fun toString(): String {
        return "$name - $description, $status, $quantity, $price"
    }
}