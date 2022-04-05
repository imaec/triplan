package com.imaec.triplan.ui.main.myplace

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.databinding.FragmentMyPlaceBinding
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
                    MyPlaceState.OnClickWrite -> {
                        requireActivity().activityResultRegistry.register(
                            WritePlaceActivity.WRITE_PLACE,
                            ActivityResultContracts.StartActivityForResult()
                        ) {
                            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                                // TODO 내 장소 리스트 갱신
                            }
                        }.launch(
                            Intent(
                                requireContext(),
                                WritePlaceActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }
}
