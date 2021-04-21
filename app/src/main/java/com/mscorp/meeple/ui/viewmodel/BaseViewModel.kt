package com.mscorp.meeple.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.mscorp.meeple.api.ApiService

open class BaseViewModel() : ViewModel(){
    var api = ApiService.getApi()
}