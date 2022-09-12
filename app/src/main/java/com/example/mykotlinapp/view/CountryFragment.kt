package com.example.mykotlinapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mykotlinapp.R
import com.example.mykotlinapp.adapter.CountryAdapter
import com.example.mykotlinapp.util.downloadFromUrl
import com.example.mykotlinapp.util.placeHolderProgressBar
import com.example.mykotlinapp.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel

    private var countryUuid = 0 ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)



        observableLiveData()

    }

    private fun observableLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                countryName.text = country.countryName
                countryRegion.text = country.countryRegion
                countryCapital.text = country.countryCapital
                countryLanguage.text = country.countryLanguage
                countryCurrency.text = country.countryCurrency
                context?.let {
                    countryImage.downloadFromUrl(country.countryImageUrl, placeHolderProgressBar(it))
                }
            }
        }
        )
    }

}