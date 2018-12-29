package com.example.corina.trackseries.user

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.corina.trackseries.R
import com.example.corina.trackseries.network.LoginNetworkApiAdapter
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.app_bar_user.*
import kotlinx.android.synthetic.main.nav_header.*
import org.jetbrains.anko.doAsync

class UserActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var userUsername: String = ""
    private var userId: String = ""
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)



        userUsername = intent.getStringExtra("username")
        userId = intent.getStringExtra("accountId")
        userEmail = intent.getStringExtra("email")
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        accountUsername.text = userUsername
        accountEmail.text = userEmail
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
                    networkApiAdapter.logout(userEmail)
                    finish()
                }

            }
            R.id.nav_settings -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
