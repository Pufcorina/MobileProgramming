package com.example.cwori.trackseries

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class NetworkAPIAdapter private constructor() {

    private object Holder {
        val INSTANCE = NetworkAPIAdapter()
    }

    companion object {
        val instance: NetworkAPIAdapter by lazy { Holder.INSTANCE }
        const val BASE_URL: String = "http://192.168.43.208:3000"
        private const val URL_ORDERS_ALL: String = "/shows"
        private const val URL_ORDER_INDIVIDUAL: String = "/shows/{id}"
    }

    private val showService: ShowsService

    init {
        val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(NetworkAPIAdapter.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        showService = retrofit.create(ShowsService::class.java)
    }

    fun fetchAll(): List<Show> {
        return showService.fetchAll().execute().body()!!
    }

    fun insert(dto: Show): Observable<Show> {
        Log.d("INSERT", dto.toString())
        return showService.insert(dto.title!!, dto.producer!!, dto.description!!, dto.rating!!)
    }

    fun update(id: String, dto: Show): Observable<ResponseBody> {
        return showService.update(id, dto.title!!, dto.producer!!, dto.description!!, dto.rating!!)
    }

    fun delete(id: String): Observable<ResponseBody> {
        return showService.delete(id)
    }

    interface ShowsService {
        @GET(URL_ORDERS_ALL)
        fun fetchAll(): Call<List<Show>>

        @POST(URL_ORDERS_ALL)
        @FormUrlEncoded
        fun insert(@Field("title") title: String,
                   @Field("producer") producer: String,
                   @Field("description") description: String,
                   @Field("rating") rating: Int
        ): Observable<Show>

        @PUT(URL_ORDER_INDIVIDUAL)
        @FormUrlEncoded
        fun update(@Path("id") id: String,
                   @Field("title") title: String,
                   @Field("producer") producer: String,
                   @Field("description") description: String,
                   @Field("rating") rating: Int
        ): Observable<ResponseBody>

        @DELETE(URL_ORDER_INDIVIDUAL)
        fun delete(@Path("id") id: String): Observable<ResponseBody>
    }
}