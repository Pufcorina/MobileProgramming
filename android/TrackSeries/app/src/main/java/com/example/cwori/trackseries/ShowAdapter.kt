package com.example.cwori.trackseries

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.list_item.view.*

class ShowAdapter(var realm: Realm, var context: Context) : RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {

    class ShowViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAdapter.ShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowAdapter.ShowViewHolder, position: Int) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        val shows = realm.where<Show>().findAll()

        holder.view.name.text = shows[position]?.title

        holder.view.btnEdit.setOnClickListener {
            val id = shows[position]?.id
            val intent = Intent(it.context, DetailActivity::class.java).apply {
                putExtra("id", id)
            }

            it.context.startActivity(intent)
        }

        holder.view.btnDelete.setOnClickListener { it ->
            val id = shows[position]?.id

            if (networkInfo != null && networkInfo.isConnected) {
                realm.executeTransaction { realm ->
                    val show = realm.where<Show>().equalTo("id", id).findFirst()!!
                    show.deleteFromRealm()
                }

                val networkApiAdapter = NetworkAPIAdapter.instance
                if (id != null) {
                    networkApiAdapter.delete(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( {}, {}, {
                            Log.d("DELETE FINISHED", "gata boss")
                        })
                }
            } else
                Toast.makeText(context, "No internet connection",Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount() = realm.where<Show>().findAll().size
}