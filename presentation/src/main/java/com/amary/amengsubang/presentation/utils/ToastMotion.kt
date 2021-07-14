package com.amary.amengsubang.presentation.utils

import android.app.Activity
import androidx.core.content.res.ResourcesCompat
import com.amary.amengsubang.presentation.R
import www.sanju.motiontoast.MotionToast

class ToastMotion(private val activity: Activity) {
    companion object{
        private const val INSERT_FAV_MSG ="Data has been added to favorites"
        private const val DELETE_FAV_MSG ="Data has been removed from favorites"
        private const val SUCCESS_CRITICS_MSG ="Thank you, message has been sent"
        private const val ERROR_CRITICS_MSG ="Something is wrong, please check your connection"
        const val SUCCESS_INSERT = 1
        const val SUCCESS_DELETE = 2
        const val SUCCESS_SEND_MSG = 3
        const val ERROR_SEND_MSG = 4
    }

    fun show(msg: Int){
        val msgTitle = when (msg) {
            SUCCESS_INSERT -> "Success"
            SUCCESS_DELETE -> "Success"
            SUCCESS_SEND_MSG -> "Success"
            ERROR_SEND_MSG -> "Error"
            else -> "Error"
        }

        val msgBody = when (msg) {
            SUCCESS_INSERT -> INSERT_FAV_MSG
            SUCCESS_DELETE -> DELETE_FAV_MSG
            SUCCESS_SEND_MSG -> SUCCESS_CRITICS_MSG
            ERROR_SEND_MSG -> ERROR_CRITICS_MSG
            else -> ERROR_CRITICS_MSG
        }

        val msgType = when (msg) {
            SUCCESS_INSERT -> MotionToast.TOAST_SUCCESS
            SUCCESS_DELETE -> MotionToast.TOAST_INFO
            SUCCESS_SEND_MSG -> MotionToast.TOAST_SUCCESS
            ERROR_SEND_MSG -> MotionToast.TOAST_ERROR
            else -> MotionToast.TOAST_ERROR
        }

        MotionToast.createColorToast(
            activity,
            msgTitle,
            msgBody,
            msgType,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(activity, R.font.helvetica_regular))
    }
}