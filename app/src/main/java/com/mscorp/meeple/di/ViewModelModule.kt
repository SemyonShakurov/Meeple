package com.mscorp.meeple.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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


@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
internal abstract class ViewModelModule {

    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @Binds
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    @Binds
    abstract fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @IntoMap
    @ViewModelKey(UserViewModel::class)
    @Binds
    abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel

    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    @Binds
    abstract fun bindEventsViewModel(viewModel: EventsViewModel): ViewModel

    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

class ViewModelFactory @Inject
constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }

        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}