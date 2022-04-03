package com.imaec.triplan.ui.searchaddress

import android.os.Bundle
import androidx.activity.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivitySearchAddressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAddressActivity :
    BaseActivity<ActivitySearchAddressBinding>(R.layout.activity_search_address) {

    private val viewModel by viewModels<SearchAddressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupListener() {
        with(binding.mtbSearchAddress) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }
}
