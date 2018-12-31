package com.example.corina.trackseries.admin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.corina.trackseries.R
import com.example.corina.trackseries.adapters.ShowAdapter
import com.example.corina.trackseries.network.AdminNetworkApiAdapter
import com.example.corina.trackseries.network.LoginNetworkApiAdapter
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.app_bar_admin.*
import kotlinx.android.synthetic.main.content_admin.*
import kotlinx.android.synthetic.main.nav_header.*
import org.jetbrains.anko.doAsync

class AdminActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var adminUsername: String = ""
    private var adminId: String = ""
    private var adminEmail: String = ""

    private lateinit var adapter: ShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        adminUsername = intent.getStringExtra("username")
        adminId = intent.getStringExtra("accountId")
        adminEmail = intent.getStringExtra("email")

        adapter = ShowAdapter(this)
        show_list.layoutManager = LinearLayoutManager(this)
        show_list.adapter = adapter
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        accountUsername.text = adminUsername
        accountEmail.text = adminEmail
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_all -> {

            }
            R.id.nav_logout -> {
                doAsync {
                    val networkApiAdapter = LoginNetworkApiAdapter.instance
                    networkApiAdapter.logout(adminEmail)
                    finish()
                }
            }
            R.id.nav_settings -> {

            }

            R.id.nav_add -> {
                // Handle the camera action
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
