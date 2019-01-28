package com.example.exam

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.exam.local_db.DbManager
import kotlinx.android.synthetic.main.activity_entity_detail.*

class EntityDetailActivity : AppCompatActivity() {
    private val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entity_detail)

        titleDetail.text = intent.getStringExtra("title")
        descriptionDetail.text = intent.getStringExtra("description")
        genreDetail.text = intent.getStringExtra("genre")
        albumDetail.text = intent.getStringExtra("album")
        yearDetail.text = intent.getStringExtra("year")

        favBtn.setOnClickListener {
            val values = ContentValues()
            values.put("title", intent.getStringExtra("title"))
            values.put("description", intent.getStringExtra("description"))
            values.put("album", intent.getStringExtra("album"))
            values.put("genre", intent.getStringExtra("genre"))
            values.put("year", intent.getStringExtra("year").toInt())

            dbManager.insert(values)

        }
    }
}
