package com.mscorp.meeple.viewmodel

import androidx.lifecycle.ViewModel
import com.mscorp.meeple.api.ApiService

open class BaseViewModel : ViewModel(){
    var api = ApiService.getApi()
}