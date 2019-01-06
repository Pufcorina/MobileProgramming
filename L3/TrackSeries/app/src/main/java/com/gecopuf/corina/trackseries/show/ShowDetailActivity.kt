package com.gecopuf.corina.trackseries.show

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gecopuf.corina.trackseries.R
import kotlinx.android.synthetic.main.activity_show_detail.*

class ShowDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)

        description.text = intent.extras.getString("description")
        producer.text = intent.extras.getString("producer")
        titleShow.text = intent.extras.getString("title")
        rating.text = intent.extras.getString("rating")
    }
}
