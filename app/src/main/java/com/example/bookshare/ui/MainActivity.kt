package com.example.bookshare.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.bookshare.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setUpNavigation()
    }

    private fun setUpNavigation() {



        NavigationUI.setupWithNavController(app_bottom_nav,app_nav_host.findNavController())
       app_nav_host.findNavController().addOnDestinationChangedListener{_,destination,_->
            when(destination.id){
                R.id.profileEditFragment->{
                    app_bottom_nav.visibility = View.GONE
                }
                else->app_bottom_nav.visibility = View.VISIBLE
            }
        }
        app_nav_host.findNavController().graph.startDestination = app_nav_host.findNavController().currentDestination!!.id
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
