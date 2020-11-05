package com.amary.amengsubang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.amary.amengsubang.R
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BubbleNavigationChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setNavigationChangeListener(this)
    }

    override fun onNavigationChanged(view: View?, position: Int) {
        when (position) {
            0 -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_home)
            1 -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_favorite)
            2 -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_map)
            3 -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_info)
        }
    }
}