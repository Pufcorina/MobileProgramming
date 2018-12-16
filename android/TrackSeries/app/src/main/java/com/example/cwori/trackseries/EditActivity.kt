package com.example.cwori.trackseries

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)

        val id = intent.getStringExtra("id")

        realm = Realm.getDefaultInstance()

        init(id)

        fab.setOnClickListener {
            this.saveShow(id)
            super.onBackPressed()
        }
    }

    private fun saveShow(id: String) {
        val show = realm.where<Show>().equalTo("id", id).findFirst()
        if (show != null) {
            realm.executeTransaction {
                show.title = edit_title.text.toString()
                show.description = edit_description.text.toString()
                show.rating = edit_stars.text.toString().toInt()
                show.producer = edit_producer.text.toString()
            }
        }
    }

    fun init(id: String) {
        title = id

        val show = realm.where<Show>().equalTo("id", id).findFirst()

        Log.d("EDIT PAGE", show.toString())

        if (show != null) {
            edit_title.text = Editable.Factory.getInstance().newEditable(show.title)
            edit_description.text = Editable.Factory.getInstance().newEditable(show.description)
            edit_stars.text = Editable.Factory.getInstance().newEditable(show.rating.toString())
            edit_producer.text = Editable.Factory.getInstance().newEditable(show.producer.toString())
        }
    }
}
