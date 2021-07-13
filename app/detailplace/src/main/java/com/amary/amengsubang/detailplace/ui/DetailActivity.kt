package com.amary.amengsubang.detailplace.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.amary.amengsubang.detailplace.R
import com.amary.amengsubang.detailplace.databinding.ActivityDetailBinding
import com.amary.amengsubang.detailplace.di.detailModule
import com.amary.amengsubang.domain.utils.Resource
import com.amary.amengsubang.presentation.model.Favorite
import com.amary.amengsubang.presentation.model.Place
import com.amary.amengsubang.presentation.model.mapToPresentation
import com.amary.amengsubang.presentation.utils.Constant.PLACE_TO_DETAIL
import com.amary.amengsubang.presentation.utils.loadVideo
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.gson.Gson
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class DetailActivity : AppCompatActivity() {

    private val detailViewMode: DetailViewModel by viewModel()
    private var binding: ActivityDetailBinding? = null
    private lateinit var mapboxMap: MapboxMap
    private var isFavorite = false
    private var place: Place? = null

    companion object {
        private const val ICON_ID = "ICON_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(detailModule)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        place = intent.getSerializableExtra(PLACE_TO_DETAIL) as Place

        initView(savedInstanceState)
        getFavoriteState()
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding?.let {
            setSupportActionBar(toolbar_detail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = getString(R.string.title)

            it.mapView.onCreate(savedInstanceState)

            val ytReview = (supportFragmentManager.findFragmentById(R.id.yt_review) as YouTubePlayerSupportFragmentX)

            getDetailData(ytReview)
        }
    }



    private fun getDetailData(ytReview: YouTubePlayerSupportFragmentX?) {
        place?.id?.let { id ->
            detailViewMode.getDetailPlace(id).observe(this, {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            val result = it.data.mapToPresentation()
                            val food = result.detail.facility.find { facility -> "Food" == facility } != null
                            val drink = result.detail.facility.find { facility -> "Drink" == facility } != null
                            val lodging = result.detail.facility.find { facility -> "Lodging" == facility } != null
                            val mosque = result.detail.facility.find { facility -> "Mosque" == facility } != null

                            binding?.placeDetail = result
                            binding?.facilities = listOf(food, drink, lodging, mosque)
                            binding?.btnGoMap?.setOnClickListener {
                                // Creates an Intent that will load a map of San Francisco
                                val gmmIntentUri = Uri.parse("geo:${result.place.latitude},${result.place.longitude}?q=${result.place.name}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            }
                            ytReview?.loadVideo(result.detail.video)

                            getMapData(result.place)
                        }
                        is Resource.Error -> {
                        }
                    }
                }
            })
        }
    }



    private fun getMapData(place: Place) {
        binding?.mapView?.getMapAsync { map ->
            mapboxMap = map
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
                val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_location, null)
                BitmapUtils.getBitmapFromDrawable(drawable)?.let { style.addImage(ICON_ID, it) }

                val symbolManager = SymbolManager(binding?.mapView!!, mapboxMap, style)
                symbolManager.iconAllowOverlap = true

                val location = LatLng(place.latitude, place.longitude)
                symbolManager.create(
                        SymbolOptions()
                                .withLatLng(location)
                                .withIconImage(ICON_ID)
                                .withIconSize(1.5f)
                                .withTextField(place.name)
                                .withTextHaloColor("rgba(255, 255, 255, 100)")
                                .withTextHaloWidth(5.0f)
                                .withTextAnchor("top")
                                .withTextOffset(arrayOf(0f, 1.5f))
                                .withDraggable(true)
                )

                mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 8.0))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
            }
            R.id.action_favorite -> {
                isFavorite = if (isFavorite) {
                    item.setIcon(R.drawable.ic_favorite_false)
                    place?.let { deleteFavorite(it) }
                    false
                } else {
                    item.setIcon(R.drawable.ic_favorite_true)
                    place?.let { insertFavorite(it) }
                    true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getFavoriteState() {
        place?.id?.let { id ->
            detailViewMode.isFavorite(id).observe(this, {
                isFavorite = it == 1
            })
        }
    }

    private fun insertFavorite(place: Place) {
        detailViewMode.insertFavoritePlace(Favorite(place.id))
    }

    private fun deleteFavorite(place: Place) {
        detailViewMode.deleteFavorite(place.id)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        val itemFavoriteTrue = menu.findItem(R.id.action_favorite)
        val icon = if (isFavorite) R.drawable.ic_favorite_true else R.drawable.ic_favorite_false
        itemFavoriteTrue.setIcon(icon)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding?.mapView?.onLowMemory()
    }

    override fun onDestroy() {
        binding = null
        place = null
        unloadKoinModules(detailModule)
        super.onDestroy()
        binding?.mapView?.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        binding?.mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding?.mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding?.mapView?.onStop()
    }
}