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
    private var backPressedListener: OnBackPressedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupViewPager()
        setupListener()
    }

    override fun onBackPressed() {
        with(binding) {
            if (vpMain.currentItem == 0) {
                super.onBackPressed()
            } else {
                if (vpMain.currentItem == 1) {
                    if (backPressedListener?.onBackPressed() == true) {
                        vpMain.setCurrentItem(0, false)
                        changeFragment(R.id.nav_home)
                        return@with
                    }
                } else {
                    vpMain.setCurrentItem(0, false)
                    changeFragment(R.id.nav_home)
                }
            }
        }
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

    fun setBackPressedListener(listener: OnBackPressedListener) {
        this.backPressedListener = listener
    }

    private fun changeFragment(navId: Int) {
        binding.bnvMain.selectedItemId = navId
    }

    interface OnBackPressedListener {
        fun onBackPressed(): Boolean
    }
}
