package com.example.corina.trackseries

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }

    override fun onBackPressed() {
        finish()
    }
}
