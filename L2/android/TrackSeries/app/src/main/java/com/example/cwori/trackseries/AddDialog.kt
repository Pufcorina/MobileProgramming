package com.example.cwori.trackseries

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.view.Window
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.add_show.*

class AddDialog(private val activity: Activity) : Dialog(activity), View.OnClickListener {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(R.layout.add_show)

        btn_add.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)

        realm = Realm.getDefaultInstance()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> {
                realm.executeTransaction { realm ->
                    val show = realm.createObject<Show>(getNewId())
                    show.description = txt_producer.text.toString()
                    show.title = txt_name.text.toString()
                    show.rating = rating_edit.text.toString().toInt()
                    show.producer = txt_producer.text.toString()
                }
                Snackbar.make(v, "Added", Snackbar.LENGTH_LONG)
                dismiss()
            }

            R.id.btn_cancel -> {
                Snackbar.make(v, "Cancel", Snackbar.LENGTH_LONG)
                dismiss()
            }
        }
    }

    private fun getNewId(): String {
        val shows = realm.where<Show>().findAll()

        var id = 0

        for (show in shows) {
            try {
                var k = show.id?.toInt()
                if (k != null && k > id) {
                    id = k
                }
            } catch (e: Exception) {

            }
        }

        return (id + 1).toString()
    }
}

