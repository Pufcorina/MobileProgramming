package com.example.marketapp.networking

import com.example.marketapp.models.Product
import retrofit2.Call
import retrofit2.http.*

interface NInterface {
    @GET("/products")
    fun listAll(): Call<List<Product>>

    @POST("/product")
    fun add(
        @Body product: Product
    ): Call<Product>

    @POST("/videogames/delete/{id}")
    fun deleteVideogame(@Path("id") id: String): Call<Void>

    @POST("/videogames/update/{id}/{name}/{genre}/{description}/{os}/{year}/{price}")
    fun updateVideoGame(
        @Path("id") id: String, @Path("name") name: String, @Path("genre") genre: String,
        @Path("description") description: String, @Path("os") os: String,
        @Path("year") year: String, @Path("price") price: String
    ): Call<Void>

}