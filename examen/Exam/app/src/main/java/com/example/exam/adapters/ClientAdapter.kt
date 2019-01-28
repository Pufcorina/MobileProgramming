package com.example.exam.adapters

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.exam.R
import com.example.exam.networking.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.special_view.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

class ClientAdapter(val context: Context) :
    RecyclerView.Adapter<ClientAdapter.ElementViewAdapter>() {

    val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<String> = ArrayList()

    init {
        refreshElements()
    }


    class ElementViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientAdapter.ElementViewAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_client, parent, false)

        return ElementViewAdapter(view)
    }

    override fun onBindViewHolder(holder: ElementViewAdapter, position: Int) {
        holder.view.title.text = elementsList[position]
    }

    override fun getItemCount() = elementsList.size

    fun refreshElements() {
        if (checkOnline()) {
            client.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        elementsList.clear()
                        elementsList.addAll(result)
                        notifyDataSetChanged()
                        Log.d("Elements -> ", elementsList.toString())
                    },
                    { throwable ->
                        if (throwable is HttpException) {
                            val body: ResponseBody = throwable.response().errorBody()!!
                            Toast.makeText(
                                context,
                                "Error: ${JSONObject(body.string()).getString("text")}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
        } else {
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
        return false

    }

}