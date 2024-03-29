package com.amary.amengsubang.map.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.amary.amengsubang.domain.utils.Resource
import com.amary.amengsubang.map.R
import com.amary.amengsubang.map.di.mapModule
import com.amary.amengsubang.map.ui.MapBottomFragment.Companion.MAP_BOTTOM_INFO
import com.amary.amengsubang.presentation.model.Place
import com.amary.amengsubang.presentation.model.mapToPresentation
import com.amary.amengsubang.presentation.utils.Constant
import com.amary.amengsubang.presentation.utils.ListCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
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
import java.util.*

class MapFragment : Fragment(), ListCallback {

    private val viewModel: MapViewModel by viewModel()
    private lateinit var mapboxMap: MapboxMap
    private var bottomSheetDialog: BottomSheetDialogFragment? = null

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
                bottomSheetDialog = MapBottomFragment(this)

                val data = Gson().fromJson(it.data, Place::class.java)
                val bundle = Bundle()
                bundle.putSerializable(MAP_BOTTOM_INFO, data)

                bottomSheetDialog?.arguments = bundle
                bottomSheetDialog?.show((context as AppCompatActivity).supportFragmentManager, "mapInfo")
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
        bottomSheetDialog = null
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onClick(view: View, place: Place) {
        val intent = Intent(context, Class.forName(Constant.ACTIVITY_DETAIL))
        intent.putExtra(Constant.PLACE_TO_DETAIL, place)
        startActivity(intent)
    }

}