package com.amary.amengsubang.presentation.utils

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.securepreferences.SecurePreferences

class Preference(context: Context) {
    companion object{
        private const val MASTER_KEY ="_androidx_security_master_key_"
        private const val PREFS_NAME = "settings"
        const val DARK_MODE = "dark_mode"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val spec = KeyGenParameterSpec.Builder(
        MASTER_KEY,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .build()

    @RequiresApi(Build.VERSION_CODES.M)
    private val masterKey= MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private var pref = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } else {
        SecurePreferences(context, Preference::class.simpleName, PREFS_NAME)
    }

    private var editor= pref.edit()

    fun setDataDarkMode(value: Int) = editor.putInt(DARK_MODE, value).commit()
    fun getDataDarkMode(): Int {
        return pref.getInt(DARK_MODE, 0)
    }
}