package com.example.marketapp.networking

import android.content.Context
import android.os.AsyncTask
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.marketapp.models.Product
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.RuntimeException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class NController(private val context: Context) {
    private val nInterface: NInterface
    private var elements: List<Product>? = ArrayList()

    init {
        val ip = "http://192.168.43.208"
        val port = "2024"
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MILLISECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("$ip:$port")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        nInterface = retrofit.create<NInterface>(NInterface::class.java)
    }

    fun getElements(): List<Product>? {
        val getClients = GetElements()
        try {
            getClients.execute().get()
        } catch (e: ExecutionException) {
            Log.d("error", e.message)
        } catch (e: InterruptedException) {
            Log.d("error", e.message)
        }

        return this.elements
    }

    private inner class GetElements : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg urls: Void): Void? {
            val retrofitCall = nInterface.listAll()
            var response: Response<List<Product>>? = null
            try {
                response = retrofitCall.execute()
                if (!response!!.isSuccessful) {
                    throw IOException(
                        if (response.errorBody() != null)
                            response.errorBody()!!.string()
                        else
                            "Unknown error"
                    )
                }
                elements = response.body()
            } catch (e: IOException) {
                Log.d("error", e.message)
            }

            return null
        }
    }


    fun addElement(element: Product): Product? {
        val elementClass = AddElement()
        return elementClass.execute(element).get()
    }

    private inner class AddElement : AsyncTask<Product, Void, Product>() {
        override fun doInBackground(vararg element: Product): Product? {
            val retrofitCall = nInterface.add(
                element[0]
            )
            val response: Response<Product>?
            try {
                response = retrofitCall.execute()
                if (!response!!.isSuccessful) {
                    throw IOException(
                        if (response!!.errorBody() != null)
                            response!!.errorBody()!!.string()
                        else
                            "Unknown error"
                    )
                } else
                    return response.body()
            } catch (e: IOException) {
                Log.d("error", e.message)
            }
            return null
        }

    }
}