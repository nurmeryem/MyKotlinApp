package com.example.mykotlinapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mykotlinapp.model.CountryModel

@Database(entities = arrayOf(CountryModel::class),version = 1)
abstract class CountryDatabase : RoomDatabase(){

    abstract fun countryDao() : CountryDao

    //Singleton

    companion object{

       @Volatile  private  var instance : CountryDatabase? = null

        private val lock = Any() // synchronize nin kitlenip kitlenmeyeceğini kontrol eden değişken

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            // synchronized: birden fazla thread gelip bu nesneye ulaşamıyor.
            // instance yok ise bu fonksiyona synchronized şekilde ulaşılabileceğini söylüyoruz.

            instance ?:  makeDatabase(context).also {
                //instance yoksa makeDatabase() fonk  çağırıyoruz ve instance ona eşitleniyor. ve bunu synchronized bir şekilde yapıyoruz.
                instance = it
            }
        }

        private fun makeDatabase ( context : Context) = Room.databaseBuilder(
            context.applicationContext, CountryDatabase::class.java,"countrydatabase"
        ).build()
    }
}