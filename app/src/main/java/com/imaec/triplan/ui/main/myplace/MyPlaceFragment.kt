package com.imaec.triplan.ui.main.myplace

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.PlaceDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.FragmentMyPlaceBinding
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.ext.toast
import com.imaec.triplan.ui.common.CommonDialog
import com.imaec.triplan.ui.writeplace.WritePlaceActivity
import com.imaec.triplan.ui.writeplace.WritePlaceType
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
                        startActivity<WritePlaceActivity>()
                    }
                    is MyPlaceState.OnClickPlace -> {
                        startActivity<WritePlaceActivity>(
                            WritePlaceActivity.createBundle(it.place, WritePlaceType.EDIT)
                        )
                    }
                    is MyPlaceState.OnLongClickPlace -> {
                        CommonDialog(
                            context = requireContext(),
                            title = "내 장소 삭제",
                            text = "\"${it.place.placeName}\"을(를) 삭제하시겠습니까?",
                            okCallback = {
                                viewModel.deletePlace(it.place)
                            }
                        ).show()
                    }
                    is MyPlaceState.DeletedPlace -> toast(it.message)
                }
            }
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }
}
