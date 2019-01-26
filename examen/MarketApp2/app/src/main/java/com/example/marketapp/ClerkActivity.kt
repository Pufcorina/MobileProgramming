package com.example.marketapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.marketapp.models.Product
import com.example.marketapp.networking.NController
import kotlinx.android.synthetic.main.activity_clerk.*
import kotlinx.coroutines.android.awaitFrame
import java.util.ArrayList

class ClerkActivity : AppCompatActivity() {
    internal var elements: MutableList<Product> = ArrayList<Product>()
    private lateinit var adapter: ArrayAdapter<Product>
    private lateinit var nc: NController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clerk)

        nc = NController(baseContext)

        //ADAPTER
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, elements)
        listView2.adapter = adapter

        this.getProducts()

        add_edit.setOnClickListener {startActivity(Intent(this@ClerkActivity, AddEditActivity::class.java)) }

        refresh.setOnClickListener {
            elements = ArrayList()
            getProducts()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getProducts()
        super.onActivityResult(requestCode, resultCode, data)
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

}

