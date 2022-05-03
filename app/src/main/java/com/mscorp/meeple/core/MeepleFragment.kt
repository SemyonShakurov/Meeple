package com.mscorp.meeple.core

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.mscorp.meeple.di.MeepleFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

internal open class MeepleFragment<VM : MeepleViewModel> : DaggerFragment() {

    @Inject
    protected lateinit var _factory: MeepleFactory<VM>
    private val viewModelProvider by viewModels<ViewModel>(factoryProducer = { _factory })

    protected val viewModel
        get() = viewModelProvider as VM

    inline fun <reified VM2 : MeepleViewModel> createViewModel(): VM2 {
        return _factory.create(VM2::class.java)
    }
}