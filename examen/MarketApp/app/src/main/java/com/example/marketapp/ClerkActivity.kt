package com.example.marketapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marketapp.models.Product
import com.example.marketapp.networking.NController
import kotlinx.android.synthetic.main.activity_clerk.*
import java.util.ArrayList

class ClerkActivity : AppCompatActivity() {
    internal var elements: MutableList<Product> = ArrayList<Product>()
    private lateinit var adapter: ArrayAdapter<Product>
    private lateinit var nc: NController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clerk)
        setSupportActionBar(toolbar)

        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        nc = NController(baseContext)

        //ADAPTER
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, elements)
        listView2.adapter = adapter

        this.getProducts()

        add_edit.setOnClickListener {getProducts()}
    }



    private fun getProducts() {
        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            elements = nc.getElements() as MutableList<Product>
            elements.sortBy { it.quantity }
            elements.reverse()
        } else {
            Toast.makeText(baseContext, "No Internet Connection", Toast.LENGTH_LONG).show()
        }

        //ADAPTER
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, elements)
        listView2.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.buttons, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.btn_refresh -> {
                elements = ArrayList()
                getProducts()
            }

            android.R.id.home -> { finish() }
        }


        return super.onOptionsItemSelected(item)
    }

}
