package com.imaec.triplan.ui.main.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel by viewModels<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }
}
