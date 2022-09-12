package com.example.mykotlinapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mykotlinapp.model.CountryModel

@Dao
interface CountryDao {

    // Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: CountryModel) : List<Long>

    //Insert - > insert into
    //suspend -> coroutine, pause & resume
    //vararg-> country listesi veriyoruz ancak tek tek veriyoruz ve bu bir liste dondurecek... multiple country object
    // List<Long> -> primary keys

    @Query("SELECT * FROM countrymodel")
    suspend fun getAllCountries() : List<CountryModel>

    @Query("SELECT * FROM countrymodel WHERE uuid = :countryid ")
    suspend fun getCountry(countryid : Int) : CountryModel

    @Query("Delete FROM countrymodel")
    suspend fun  deleteAllCountries()
}