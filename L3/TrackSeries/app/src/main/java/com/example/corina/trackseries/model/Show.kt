package com.example.corina.trackseries.model


class Show {
    var id: String? = null
    var title: String? = null
    var producer: String? = null
    var description: String? = null
    var rating: Int? = null

    constructor() {}

    constructor(id: String, title: String, producer: String, description: String, rating: Int) {
        this.id = id
        this.title = title
        this.producer = producer
        this.description = description
        this.rating = rating
    }

    constructor(title: String, producer: String, description: String, rating: Int) {
        this.title = title
        this.producer = producer
        this.description = description
        this.rating = rating
    }

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["title"] = title!!
        result["producer"] = producer!!
        result["description"] = description!!
        result["rating"] = rating!!

        return result
    }
}