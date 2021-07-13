package com.amary.amengsubang.map.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amary.amengsubang.map.R
import com.amary.amengsubang.presentation.model.Place
import com.amary.amengsubang.presentation.utils.BindingInstance.bindImage
import com.amary.amengsubang.presentation.utils.ListCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_item.view.*


class MapBottomFragment(private val callback: ListCallback) : BottomSheetDialogFragment() {

    companion object{
        const val MAP_BOTTOM_INFO = "map_bottom_info"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_item, container, false)
        val data = arguments?.getSerializable(MAP_BOTTOM_INFO) as Place

        view.tv_item_name.text = data.name
        view.tv_item_district.text = data.district
        view.img_place.bindImage(data.image)
        view.btn_detail.setOnClickListener {
            callback.onClick(it, data)
            dismiss()
        }

        return view
    }

}