package com.mscorp.meeple.core

import androidx.lifecycle.ViewModel
import com.mscorp.meeple.model.Request
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

internal open class MeepleViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    protected inline fun <T : Any> Single<T>.loadResource(
        crossinline onSetResult: (Request<T>) -> Unit,
    ) = loadResource(
        onLoading = { loadingResource -> onSetResult.invoke(loadingResource) },
        onSuccess = { onSuccessResource -> onSetResult.invoke(onSuccessResource) },
        onError = { onFailedResource -> onSetResult.invoke(onFailedResource) }
    )

    protected inline fun <T : Any> Single<T>.loadResource(
        crossinline onLoading: (Request.Loading) -> Unit = { /* No-op */ },
        crossinline onError: (Request.Failure) -> Unit = { /* No-op */ },
        crossinline onSuccess: (Request.Success<T>) -> Unit = { /* No-op */ },
    ) {
        onLoading.invoke(Request.Loading)

        val disposable = subscribeOn(Schedulers.io()).subscribeBy(
            onError = {
                onError.invoke(
                    Request.Failure(
                        isNetworkError = true,
                        errorCode = 0,
                        errorBody = "",
                    )
                )
                // TODO: Timber.d()
            },
            onSuccess = { onSuccess.invoke(Request.Success(it)) }
        )

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}