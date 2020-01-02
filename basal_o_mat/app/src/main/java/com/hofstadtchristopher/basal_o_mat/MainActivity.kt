package com.hofstadtchristopher.basal_o_mat

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    //private lateinit var textMessage: TextView
    /*private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_start -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_test -> {
                //textMessage.setText(R.string.title_test)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_protocol -> {
                    getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
                    getSupportActionBar()?.setHomeButtonEnabled(false)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set Top-Level Destination to handle correct up-navigation's (pressing back)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_start,
            R.id.navigation_test,
            R.id.navigation_protocol,
            R.id.navigation_profil,
            R.id.navigation_more
        ).build()
        //setSupportActionBar(toolbar)

        //val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        //setupActionBar(navController)

        //val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //textMessage = findViewById(R.id.message)
        //navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        //test
        //nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)

        /* toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)
        NavigationUI.setupWithNavController(toolbar, navController)
         */

        //actionbar
        //NavigationUI.setupActionBarWithNavController(this, navController)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        //setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    //private fun setupActionBar(navController: NavController) {
    //    NavigationUI.setupActionBarWithNavController(this, navController)
    //}


}
