package com.amary.amengsubang.detailplace.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class DetailActivity : AppCompatActivity() {

    private val detailViewMode: DetailViewModel by viewModel()
    private var binding: ActivityDetailBinding? = null
    private var isFavorite = false
    private var place: Place? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(detailModule)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        place = intent.getSerializableExtra(PLACE_TO_DETAIL) as Place

        initView()
        getFavoriteState()
    }

    private fun initView() {
        binding?.let {
            setSupportActionBar(toolbar_detail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = getString(R.string.title)
            val ytReview = (supportFragmentManager.findFragmentById(R.id.yt_review) as YouTubePlayerSupportFragmentX)

            getDetailData(ytReview)
        }
    }

    private fun getDetailData(ytReview: YouTubePlayerSupportFragmentX?) {
        place?.id?.let {id ->
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
                            ytReview?.loadVideo(result.detail.video)
                        }
                        is Resource.Error -> {
                        }
                    }
                }
            })
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

    override fun onDestroy() {
        binding = null
        place = null
        unloadKoinModules(detailModule)
        super.onDestroy()
    }
}