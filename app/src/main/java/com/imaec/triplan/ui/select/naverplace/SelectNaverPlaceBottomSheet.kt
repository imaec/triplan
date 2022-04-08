package com.imaec.triplan.ui.select.naverplace

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.NaverPlaceDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseBottomSheetDialogFragment
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.BottomSheetNaverPlaceBinding
import com.imaec.triplan.ui.common.CommonBottomListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectNaverPlaceBottomSheet :
    BaseBottomSheetDialogFragment<BottomSheetNaverPlaceBinding>(R.layout.bottom_sheet_naver_place) {

    private val viewModel by viewModels<SelectNaverPlaceViewModel>()

    @Suppress("UNCHECKED_CAST")
    private val listener: CommonBottomListener<NaverPlaceDto>? by lazy {
        arguments?.getSerializable(SELECT_PLACE_LISTENER) as? CommonBottomListener<NaverPlaceDto>
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        binding.rvBottomSheet.run {
            val diffUtil = object : DiffUtil.ItemCallback<NaverPlaceDto>() {
                override fun areItemsTheSame(
                    oldItem: NaverPlaceDto,
                    newItem: NaverPlaceDto
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: NaverPlaceDto,
                    newItem: NaverPlaceDto
                ): Boolean = oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_naver_place,
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
                    is SelectNaverPlaceState.OnClickPlace -> {
                        listener?.onBottomSelected(it.place)
                        dismiss()
                    }
                }
            }
        }
    }

    companion object {
        const val LIST = "list"
        const val SELECT_PLACE_LISTENER = "select_place_listener"

        fun instance(
            manager: FragmentManager,
            list: List<NaverPlaceDto>,
            listener: CommonBottomListener<NaverPlaceDto>? = null,
        ) {
            val tagName = SelectNaverPlaceBottomSheet::class.java.simpleName
            if (manager.findFragmentByTag(tagName) == null) {
                SelectNaverPlaceBottomSheet().apply {
                    arguments = bundleOf(
                        LIST to list,
                        SELECT_PLACE_LISTENER to listener
                    )
                }.show(manager, tagName)
            }
        }
    }
}
