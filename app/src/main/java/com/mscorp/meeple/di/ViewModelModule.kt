package com.mscorp.meeple.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mscorp.meeple.core.MeepleViewModel
import com.mscorp.meeple.features.core_feature.view_models.LoginViewModel
import com.mscorp.meeple.features.core_feature.view_models.RegistrationViewModel
import com.mscorp.meeple.features.core_feature.view_models.UserViewModel
import com.mscorp.meeple.features.event_feature.EventsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
internal abstract class ViewModelModule {

    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @Binds
    abstract fun LoginViewModel.bindLoginViewModel()

    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    @Binds
    abstract fun RegistrationViewModel.bindRegistrationViewModel()

    @IntoMap
    @ViewModelKey(UserViewModel::class)
    @Binds
    abstract fun UserViewModel.bindUserViewModel()

    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    @Binds
    abstract fun EventsViewModel.bindEventsViewModel()
}

internal class MeepleFactory<T : MeepleViewModel> @Inject constructor(
    private val viewModels: Map<Class<*>, @JvmSuppressWildcards Provider<MeepleViewModel>>,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModels.firstNotNullOf { (clazz, viewModel) ->
            if (clazz == modelClass) viewModel as T else null
        }
    }
}