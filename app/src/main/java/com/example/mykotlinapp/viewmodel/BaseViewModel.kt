package com.example.mykotlinapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) , CoroutineScope{ // bu sınıftan bir obje oluşturmayacağımız için abstract olarak tanımlamak mantıklı

    private val job = Job() // arka planda yapılacak iş

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // Coroutine nin ne yapacağını söylüyoruz.. Job ' ı yap ve main thread'e dön

    override fun onCleared() {
        super.onCleared()
        job.cancel() // eğer app context'i de giderse job iptal edilir.
    }
}