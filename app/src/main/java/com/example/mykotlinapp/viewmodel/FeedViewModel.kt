package com.example.mykotlinapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinapp.model.CountryModel

class FeedViewModel : ViewModel() {
    val countries = MutableLiveData<List<CountryModel>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val country = CountryModel("Turkey", "Asia","Ankara","TRY","Turkish","www.aaa.com")
        val country2 = CountryModel("Turkey", "Asia","Ankara","TRY","Turkish","www.aaa.com")
        val country3 = CountryModel("Turkey", "Asia","Ankara","TRY","Turkish","www.aaa.com")

        val countryList = arrayListOf<CountryModel>(country,country2,country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false

    }
}