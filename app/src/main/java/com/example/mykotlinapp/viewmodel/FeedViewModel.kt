package com.example.mykotlinapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinapp.model.CountryModel
import com.example.mykotlinapp.service.CountrtyApiServices
import com.example.mykotlinapp.service.CountryDatabase
import com.example.mykotlinapp.util.CustomSharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.security.auth.login.LoginException

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countrtyApiServices = CountrtyApiServices
    private val disposible = CompositeDisposable()
    //kullan at nesnesi / retrofitten her call yaptığımızda bu call lar hafızada bir yer kaplar. bu objeye verileri atıyoruz. clear işleminde bu veriler siliniyor ve hafızada yer kaplamamış oluyor.

    private val custumPreferences = CustomSharedPreferences(getApplication())

    val countries = MutableLiveData<List<CountryModel>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L // nanotime -> 10dk

    fun refreshData() {
        val updateTime = custumPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) // 10dk dan küçükse{
        {
            //10 dk ya geçmediyse verileri database'den çekeceğiz.
            getDataFromSQLite()
        } else {
            getDataFromAPI()

        }
    }
    fun refreshFromAPI() {
        getDataFromAPI()
    }


    private fun getDataFromSQLite() {
        countryLoading.value = true

        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "countries from sqllite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposible.add(
            countrtyApiServices.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountryModel>>() {
                    override fun onSuccess(t: List<CountryModel>) {
                        // verileri çektiğimizde sqlite da sakla
                        stroreInSQLite(t)
                        Toast.makeText(getApplication(), "countries from api", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }


    private fun showCountries(countryList: List<CountryModel>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false

    }

    private fun stroreInSQLite(list: List<CountryModel>) {  //aldığımız verileri sqlite'a kaydetmek için
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries() // daha onceden kalan veriler silindi.
            val listLong =
                dao.insertAll(*list.toTypedArray()) // listedeki verileri tekil hale getirecektir.(Tek tek) list -> induvidual
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            showCountries(list)
        }

        custumPreferences.saveTime(System.nanoTime()) // alabileceğimiz en detaylı zaman birimi , hangi
    }

    override fun onCleared() { // hafızayı daha verimli hale getirmek için
        super.onCleared()

        disposible.clear()
    }


}