package com.amary.amengsubang.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

abstract class BaseViewModel : ViewModel() {
    protected val vmScopes = viewModelScope
}