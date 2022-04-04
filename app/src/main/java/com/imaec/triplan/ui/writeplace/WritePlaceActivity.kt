package com.imaec.triplan.ui.writeplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityWritePlaceBinding
import com.imaec.triplan.ui.searchaddress.SearchAddressActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritePlaceActivity : BaseActivity<ActivityWritePlaceBinding>(R.layout.activity_write_place) {

    private val viewModel by viewModels<WritePlaceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupListener() {
        with(binding.mtbWritePlace) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@WritePlaceActivity) {
                when (it) {
                    WritePlaceState.OnClickAddress -> {
                        activityResultRegistry.register(
                            SearchAddressActivity.SEARCH_ADDRESS,
                            ActivityResultContracts.StartActivityForResult()
                        ) {
                            if (it.resultCode == RESULT_OK) {
                                it.data?.getStringExtra(SearchAddressActivity.ADDRESS)?.let {
                                    viewModel.setAddress(it)
                                }
                            }
                        }.launch(
                            Intent(
                                this@WritePlaceActivity,
                                SearchAddressActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }
}
