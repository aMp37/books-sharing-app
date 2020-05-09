package com.example.bookshare.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.bookshare.R
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(app_bottom_nav,app_nav_host.findNavController())
    }
}
