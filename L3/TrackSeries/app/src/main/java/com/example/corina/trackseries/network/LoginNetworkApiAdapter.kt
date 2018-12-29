package com.example.corina.trackseries.network

import android.util.Log
import com.example.corina.trackseries.model.Account
import com.example.corina.trackseries.model.AccountInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

class LoginNetworkApiAdapter private constructor() {
    private object Holder {
        val INSTANCE = LoginNetworkApiAdapter()
    }

    companion object {
        val instance: LoginNetworkApiAdapter by lazy { Holder.INSTANCE }
        const val BASE_URL: String = "http://192.168.0.12:3000"
    }

    private val loginService: LoginService

    init {
        val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(LoginNetworkApiAdapter.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        loginService = retrofit.create(LoginService::class.java)
    }

    fun login(username: String, password: String): AccountInfo {
        val data = loginService.login(username, password).execute().body()!!

        return AccountInfo(
            data.asJsonObject.get("accountId").asInt,
            data.asJsonObject.get("connected").asInt,
            data.asJsonObject.get("email").asString,
            data.asJsonObject.get("password").asString,
            data.asJsonObject.get("roleId").asInt,
            data.asJsonObject.get("username").asString
        )
    }

    fun register(username: String, password: String, email: String): Boolean {
        return loginService.register(username, email, password).execute().body()!!
    }

    fun forgotPassword(email: String) : String {
        return loginService.forgotPassword(email).execute().body()!!
    }

    fun logout(email: String) : Int {
        return loginService.logout(email).execute().body()!!
    }

    interface LoginService {
        @GET("/login")
        fun login(@Query("username") username: String, @Query("password") password: String): Call<JsonElement>


        @POST("/register")
        @FormUrlEncoded
        fun register(@Field("username") username: String,
                     @Field("email") email: String,
                     @Field("password") password: String): Call<Boolean>

        @GET("/forgotPassword")
        fun forgotPassword(@Query("email") email: String) : Call<String>

        @GET("/logout")
        fun logout(@Query("email") email: String) : Call<Int>
    }
}