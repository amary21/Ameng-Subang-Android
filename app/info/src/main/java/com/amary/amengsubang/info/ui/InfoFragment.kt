package com.amary.amengsubang.info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.amary.amengsubang.domain.utils.Resource
import com.amary.amengsubang.info.R
import com.amary.amengsubang.info.di.infoModule
import com.amary.amengsubang.presentation.utils.Preference
import com.amary.amengsubang.presentation.utils.ToastMotion
import com.amary.amengsubang.presentation.utils.ToastMotion.Companion.ERROR_SEND_MSG
import com.amary.amengsubang.presentation.utils.ToastMotion.Companion.SUCCESS_SEND_MSG
import kotlinx.android.synthetic.main.info_about_body.*
import kotlinx.android.synthetic.main.info_fragment.*
import kotlinx.android.synthetic.main.info_setting_body.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf

class InfoFragment : Fragment(), RadioGroup.OnCheckedChangeListener {

    private val infoViewModel: InfoViewModel by viewModel()

    private val preference: Preference by inject()
    private val toastMotion: ToastMotion by inject{ parametersOf(activity)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(infoModule)
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        exp_settings.parentLayout.setOnClickListener { exp_settings.toggleLayout() }
        exp_about.parentLayout.setOnClickListener { exp_about.toggleLayout() }

        getThemePreference()
        rb_theme.setOnCheckedChangeListener(this)

        getInputForm()
    }

    private fun getInputForm() {
        btn_send.setOnClickListener {
            val name = edt_name.editText?.text.toString()
            val message = edt_kritik.editText?.text.toString()

            if (name.isEmpty()){
                edt_name.error = "please insert your name"
            } else {
                edt_name.error = null
            }

            if (message.isEmpty()){
                edt_kritik.error = "please insert your message"
            } else {
                edt_kritik.error = null
            }

            if (name.isNotEmpty() && message.isNotEmpty()){
                edt_name.error = null
                edt_kritik.error = null
                infoViewModel.sendMessage(name, message).observe(viewLifecycleOwner, {msg ->
                    if (msg != null) {
                        when (msg) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                toastMotion.show(SUCCESS_SEND_MSG)
                                edt_name.editText?.text = null
                                edt_kritik.editText?.text = null
                            }
                            is Resource.Error -> toastMotion.show(ERROR_SEND_MSG)
                        }
                    }
                })
            }
        }

    }

    private fun getThemePreference() {
        when (preference.getDataDarkMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> {
                rb_default.isChecked = false
                rb_light.isChecked = false
                rb_dark.isChecked = true
            }
            AppCompatDelegate.MODE_NIGHT_NO -> {
                rb_default.isChecked = false
                rb_light.isChecked = true
                rb_dark.isChecked = false
            }
            else -> {
                rb_default.isChecked = true
                rb_light.isChecked = false
                rb_dark.isChecked = false
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        var theme = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        val rb = group.findViewById<RadioButton>(checkedId)
        if (rb != null) {
            when (checkedId) {
                R.id.rb_default -> {
                    rb.isChecked = true
                    theme = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                R.id.rb_light -> {
                    rb.isChecked = true
                    theme = AppCompatDelegate.MODE_NIGHT_NO
                }
                R.id.rb_dark -> {
                    rb.isChecked = true
                    theme = AppCompatDelegate.MODE_NIGHT_YES
                }
            }

            AppCompatDelegate.setDefaultNightMode(theme)
            preference.setDataDarkMode(theme)
        }

    }

}