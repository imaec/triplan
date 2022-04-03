package com.imaec.triplan.ui.writeplace

import android.os.Bundle
import androidx.activity.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityWritePlaceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritePlaceActivity : BaseActivity<ActivityWritePlaceBinding>(R.layout.activity_write_place) {

    private val viewModel by viewModels<WritePlaceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
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
}
