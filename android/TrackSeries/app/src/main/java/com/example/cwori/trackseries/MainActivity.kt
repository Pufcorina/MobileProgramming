package com.example.cwori.trackseries

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var adapter: ShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        Realm.deleteRealm(Realm.getDefaultConfiguration())
//         This will automatically trigger the migration if needed
        realm = Realm.getDefaultInstance()

//        realm.executeTransaction { realm ->
//            realm.deleteAll()
//        }

//        realm.executeTransaction { realm ->
//            val show = realm.createObject<Show>(0)
//            show.description = "ceva descriere random"
//            show.title = "ceva titlu pe acolo"
//            show.rating = 3
//        }

        fab.setOnClickListener {
            val dialog = AddDialog(this)
            dialog.show()
        }

        adapter = ShowAdapter(realm, baseContext)
        rv_item_list.layoutManager = LinearLayoutManager(this)
        rv_item_list.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.buttons, menu)
        return true
    }

    @SuppressLint("CheckResult")
    fun synchronize() {
        var realm = Realm.getDefaultInstance()
        val networkApiAdapter = NetworkAPIAdapter.instance

        val serverShows = networkApiAdapter.fetchAll()
        val localShows = realm.where<Show>().findAll()
//
//        for (unwantedShow in showsToDelete) {
//            Log.d("DELETING", unwantedShow.toString())
//            networkApiAdapter.delete(unwantedShow.id!!)
//        }

        Log.d("dksandnklads", serverShows.toString())
        Log.d("----------------------", localShows.toString())

        for (localShow in localShows) {
            val id = localShow.id

            Log.d("Cwori", localShow.toString())
            if (id != null) {
                if (id.length < 4){
                    Log.d("LENGTH < 4", id)
                    networkApiAdapter.insert(localShow)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( {}, {}, {
                            Log.d("INSERT FINISHED", "gata boss")
                        })
                }else {
                    networkApiAdapter.update(id, localShow)
                    .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( {}, {}, {
                            Log.d("UPDATE FINISHED", "gata boss")
                        })
                }
            }
        }

        realm.executeTransaction { realm ->
            realm.deleteAll()
        }

        for (serverShow in serverShows) {
            realm.executeTransaction { realm ->
                val show = realm.createObject<Show>(serverShow.id)
                show.title = serverShow.title
                show.description = serverShow.description
                show.rating = serverShow.rating
                show.producer = serverShow.producer
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.btn_refresh -> {
            doAsync {
                synchronize()
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }

            }
            Toast.makeText(this.baseContext, "Synchronized", Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}

