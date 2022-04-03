package com.imaec.triplan.ui.main.myplace

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.databinding.FragmentMyPlaceBinding
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.ui.writeplace.WritePlaceActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlaceFragment : BaseFragment<FragmentMyPlaceBinding>(R.layout.fragment_my_place) {

    private val viewModel by viewModels<MyPlaceViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(viewLifecycleOwner) {
                when (it) {
                    MyPlaceState.OnClickWrite -> startActivity<WritePlaceActivity>()
                }
            }
        }
    }
}
