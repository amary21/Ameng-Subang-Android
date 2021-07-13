package com.amary.amengsubang.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary.amengsubang.domain.utils.Resource
import com.amary.amengsubang.home.R
import com.amary.amengsubang.home.di.homeModule
import com.amary.amengsubang.presentation.adapter.ListAdapter
import com.amary.amengsubang.presentation.model.Place
import com.amary.amengsubang.presentation.model.mapToPresentation
import com.amary.amengsubang.presentation.utils.Constant.ACTIVITY_DETAIL
import com.amary.amengsubang.presentation.utils.Constant.PLACE_TO_DETAIL
import com.amary.amengsubang.presentation.utils.ListCallback
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class HomeFragment : Fragment(), ListCallback {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var listAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(homeModule)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initSearchView()
    }

    private fun initViews() {
        listAdapter = ListAdapter(this)
        rv_home.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            adapter = listAdapter
        }

        sRefreshHome.setOnRefreshListener {
            sRefreshHome.isRefreshing = false
            getViewAllData()
        }

        getViewAllData()
    }

    private fun initSearchView() {
        sv_home.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getSearchData(query)
                sv_home.onActionViewCollapsed()
                return true
            }

            override fun onQueryTextChange(query: String) = false
        })

        sv_home.setOnCloseListener {
            getViewAllData()
            false
        }
    }

    private fun getViewAllData() {
        homeViewModel.getRemotePlace.observe(viewLifecycleOwner, { place ->

            if (place != null) {
                when (place) {
                    is Resource.Loading -> {
                        loading_home.visibility = View.VISIBLE
                        rv_home.visibility = View.GONE
                        view_error.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        listAdapter.addItem(place.data.mapToPresentation())
                        loading_home.visibility = View.GONE
                        rv_home.visibility = View.VISIBLE
                        view_error.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        loading_home.visibility = View.GONE
                        rv_home.visibility = View.GONE
                        view_error.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getSearchData(search: String) {
        homeViewModel.getPlaceSearch(search).observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        loading_home.visibility = View.VISIBLE
                        rv_home.visibility = View.GONE
                        view_error.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        listAdapter.addItem(it.data.mapToPresentation())
                        loading_home.visibility = View.GONE
                        rv_home.visibility = View.VISIBLE
                        view_error.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        loading_home.visibility = View.GONE
                        rv_home.visibility = View.GONE
                        view_error.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onClick(view: View, place: Place) {
        val intent = Intent(context, Class.forName(ACTIVITY_DETAIL))
        intent.putExtra(PLACE_TO_DETAIL, place)
        startActivity(intent)
    }

    override fun onDestroyView() {
        rv_home.adapter = null
        unloadKoinModules(homeModule)
        super.onDestroyView()
    }

}