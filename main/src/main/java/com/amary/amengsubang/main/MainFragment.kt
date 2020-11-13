package com.amary.amengsubang.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), BubbleNavigationChangeListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nav_view.setNavigationChangeListener(this)
    }

    override fun onNavigationChanged(view: View?, position: Int) {
        when (position) {
            0 -> activity?.let { Navigation.findNavController(it, R.id.nav_host_main).navigate(R.id.nav_home) }
            1 -> activity?.let { Navigation.findNavController(it, R.id.nav_host_main).navigate(R.id.nav_favorite) }
            2 -> activity?.let { Navigation.findNavController(it, R.id.nav_host_main).navigate(R.id.nav_map) }
            3 -> activity?.let { Navigation.findNavController(it, R.id.nav_host_main).navigate(R.id.nav_info) }
        }
    }
}