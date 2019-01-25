package com.example.marketapp.models

class Product {
    var id: Int = 0
    var name = ""
    var description = ""
    var status = ""
    var price = 0
    var quantity = 0

    constructor(id: Int, name: String, description: String, status: String, price: Int, quantity: Int) {
        this.id = id
        this.name = name
        this.description = description
        this.status = status
        this.price = price
        this.quantity = quantity
    }

    constructor(name: String, description: String, price: Int, quantity: Int) {
        this.name = name
        this.description = description
        this.price = price
        this.quantity = quantity
    }

    constructor(p: ProductRealm) {
        this.id = p.id
        this.name = p.name
        this.description = p.description
        this.status = p.status
        this.price = p.price
        this.quantity = p.quantity
    }

    override fun toString(): String {
        return "$name -> Status: $status \n Quantity: $quantity -> Price: $price"
    }
}