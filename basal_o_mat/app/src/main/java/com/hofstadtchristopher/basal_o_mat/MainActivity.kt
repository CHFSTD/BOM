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

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

}
