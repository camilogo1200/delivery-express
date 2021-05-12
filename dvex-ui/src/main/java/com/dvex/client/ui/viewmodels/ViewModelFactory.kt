package com.dvex.client.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvex.client.application.DvexApplication
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    var application: DvexApplication
) : ViewModelProvider.NewInstanceFactory() {

    private val getSitesUseCase by lazy { application.interactorsComponent.providesGetSitesUseCase() }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass)
        {
            when {
                isAssignableFrom(SitesViewModel::class.java) -> SitesViewModel(getSitesUseCase)
                else -> throw IllegalStateException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}