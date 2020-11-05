package com.amary.amengsubang.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary.amengsubang.domain.utils.Resource
import com.amary.amengsubang.favorite.R
import com.amary.amengsubang.favorite.di.favoriteModule
import com.amary.amengsubang.presentation.adapter.ListAdapter
import com.amary.amengsubang.presentation.model.Place
import com.amary.amengsubang.presentation.model.mapToPresentation
import com.amary.amengsubang.presentation.utils.Constant.ACTIVITY_DETAIL
import com.amary.amengsubang.presentation.utils.Constant.PLACE_TO_DETAIL
import com.amary.amengsubang.presentation.utils.ListCallback
import kotlinx.android.synthetic.main.favorite_fragment.*
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FavoriteFragment : Fragment(), ListCallback {

    private val favoriteViewModel: FavoriteViewModel by inject()
    private lateinit var listAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(favoriteModule)
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initSearchView()
    }

    private fun initViews() {
        listAdapter = ListAdapter((this))
        rv_favorite.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            adapter = listAdapter
        }

        sRefreshFavorite.setOnRefreshListener {
            sRefreshFavorite.isRefreshing = false
            getViewAllData()
        }

        getViewAllData()
    }

    private fun initSearchView() {
    }

    private fun getViewAllData() {
        favoriteViewModel.getFavoriteAllPlace.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Loading -> {
                    loading_favorite.visibility = View.VISIBLE
                    rv_favorite.visibility = View.GONE
                    view_error.visibility = View.GONE
                }
                is Resource.Success -> {
                    val result = it.data.mapToPresentation().map { data -> data.place }
                    listAdapter.addItem(result as ArrayList<Place>)
                    loading_favorite.visibility = View.GONE
                    rv_favorite.visibility = View.VISIBLE
                    view_error.visibility = View.GONE
                }
                is Resource.Error -> {
                    loading_favorite.visibility = View.GONE
                    rv_favorite.visibility = View.GONE
                    view_error.visibility = View.VISIBLE
                }
            }

        })
    }

    override fun onDestroyView() {
        unloadKoinModules(favoriteModule)
        rv_favorite.adapter = null
        super.onDestroyView()
    }

    override fun onClick(view: View, place: Place) {
        val intent = Intent(context, Class.forName(ACTIVITY_DETAIL))
        intent.putExtra(PLACE_TO_DETAIL, place)
        startActivity(intent)
    }

}