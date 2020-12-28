package com.amary.amengsubang.map.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.amary.amengsubang.domain.utils.Resource
import com.amary.amengsubang.map.R
import com.amary.amengsubang.map.di.mapModule
import com.amary.amengsubang.presentation.model.Place
import com.amary.amengsubang.presentation.model.mapToPresentation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.utils.BitmapUtils
import kotlinx.android.synthetic.main.map_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import java.util.ArrayList

class MapFragment : Fragment() {

    private val viewModel: MapViewModel by viewModel()
    private lateinit var mapboxMap: MapboxMap
    private lateinit var bottomSheetDialog: BottomSheetDialog

    companion object {
        private const val ICON_ID = "ICON_IDS"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(mapModule)
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    @SuppressLint("InflateParams")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomSheetDialog = context?.let { BottomSheetDialog(it) }!!

        val view = LayoutInflater.from(context).inflate(R.layout.bottom_item, null)
        bottomSheetDialog.setContentView(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            this.mapboxMap = it
            getAllMapData()
        }
    }

    private fun getAllMapData() {
        viewModel.getRemotePlace.observe(viewLifecycleOwner,{ place ->
            if (place != null) {
                when (place) {
                    is Resource.Loading -> {
                        progress_bar.visibility = View.VISIBLE
                        mapView.visibility = View.GONE
                        view_error.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        progress_bar.visibility = View.GONE
                        mapView.visibility = View.VISIBLE
                        view_error.visibility = View.GONE
                        showMarker(place.data.mapToPresentation())
                    }
                    is Resource.Error -> {
                        progress_bar.visibility = View.GONE
                        mapView.visibility = View.GONE
                        view_error.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun showMarker(dataPlace: ArrayList<Place>) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS) {style ->
            val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_location, null)
            BitmapUtils.getBitmapFromDrawable(drawable)?.let { style.addImage(ICON_ID, it) }

            val latLngBoundsBuilder = LatLngBounds.Builder()

            val symbolManager = SymbolManager(mapView, mapboxMap, style)
            symbolManager.iconAllowOverlap = true

            val options = ArrayList<SymbolOptions>()
            dataPlace.forEach { data ->
                latLngBoundsBuilder.include(LatLng(data.latitude, data.longitude))
                options.add(
                        SymbolOptions()
                                .withLatLng(LatLng(data.latitude, data.longitude))
                                .withIconImage(ICON_ID)
                                .withData(Gson().toJsonTree(data))
                )
            }
            symbolManager.create(options)

            val latLngBounds = latLngBoundsBuilder.build()
            mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), 5000 )

            symbolManager.addClickListener {
                bottomSheetDialog.show()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}