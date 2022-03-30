package com.imaec.triplan.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }
}
