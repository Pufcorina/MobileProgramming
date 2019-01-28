package com.example.exam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.exam.adapters.SpecialAdapter
import kotlinx.android.synthetic.main.activity_special.*

class SpecialActivity : AppCompatActivity() {
    private lateinit var adapter: SpecialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_special)

        adapter = SpecialAdapter(baseContext)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        listView4.layoutManager = layoutManager
        listView4.adapter = adapter
    }
}
