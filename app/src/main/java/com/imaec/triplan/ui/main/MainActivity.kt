package com.imaec.triplan.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupViewPager()
        setupListener()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupViewPager() {
        with(binding) {
            vpMain.apply {
                adapter = MainPagerAdapter(this@MainActivity)
                offscreenPageLimit = 1
                isUserInputEnabled = false
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            bnvMain.setOnItemSelectedListener {
                vpMain.setCurrentItem(
                    when (it.itemId) {
                        R.id.nav_home -> 0
                        R.id.nav_search -> 1
                        R.id.nav_my_place -> 2
                        else -> 3
                    },
                    false
                )
                true
            }
        }
    }
}
