package com.example.mykotlinapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinapp.R
import com.example.mykotlinapp.model.CountryModel
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter (val countryList: ArrayList<CountryModel>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){
    class CountryViewHolder(val view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        // layout ile adapteri bağlamamız için inflater
        val inflater  = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
     // viewdeki elementlerimize erişebiliyoruz..
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun uptadeCountryList(newCountryList: List<CountryModel>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

}