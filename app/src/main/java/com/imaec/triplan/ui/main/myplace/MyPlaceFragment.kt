package com.imaec.triplan.ui.main.myplace

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.PlaceDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.FragmentMyPlaceBinding
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import com.imaec.triplan.ui.writeplace.WritePlaceActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlaceFragment : BaseFragment<FragmentMyPlaceBinding>(R.layout.fragment_my_place) {

    private val viewModel by viewModels<MyPlaceViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupObserver()
        setupData()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvMyPlace) {
            val diffUtil = object : DiffUtil.ItemCallback<PlaceDto>() {
                override fun areItemsTheSame(oldItem: PlaceDto, newItem: PlaceDto): Boolean =
                    oldItem.placeId == newItem.placeId

                override fun areContentsTheSame(oldItem: PlaceDto, newItem: PlaceDto): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(RecyclerViewDividerDecoration())

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_place,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
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

    private fun setupData() {
        viewModel.fetchData()
    }
}
