package com.example.mykotlinapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinapp.model.CountryModel

class CountryViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<CountryModel>()

    fun getDataFromRoom(){
        val country =  CountryModel("Turkey", "Asia", "Ankara","TRY","Turkish","www.aa.com")
        countryLiveData.value = country
    }

}