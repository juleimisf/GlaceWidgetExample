package com.makesmartapps.glacewidgetexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makesmartapps.glacewidgetexample.ui.theme.RestApi

class MainViewModelFactory(private val restApi: RestApi) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java))
            return MainActivityViewModel(MainRepository(restApi)) as T

        throw IllegalArgumentException("Unknown class name")
    }
}