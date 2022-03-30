package com.imaec.triplan.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }
}
