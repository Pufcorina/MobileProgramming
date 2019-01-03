package com.example.corina.trackseries.network

import com.example.corina.trackseries.model.Show
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class AdminNetworkApiAdapter private constructor() {
    private object Holder {
        val INSTANCE = AdminNetworkApiAdapter()
    }

    companion object {
        val instance: AdminNetworkApiAdapter by lazy { Holder.INSTANCE }
        const val BASE_URL: String = "http://192.168.0.12:3000"
    }

    private val adminService: AdminService

    init {
        val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(LoginNetworkApiAdapter.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        adminService = retrofit.create(AdminService::class.java)
    }

    fun getAllShows() : List<Show> {
        return adminService.getAllShows().execute().body()!!
    }


    interface AdminService {
        @GET("/admin/shows")
        fun getAllShows(): Call<List<Show>>
    }
}