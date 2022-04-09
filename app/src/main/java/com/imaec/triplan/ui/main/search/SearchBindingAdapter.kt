package com.imaec.triplan.ui.main.search

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.model.PlanDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.base.bindItemList

@BindingAdapter(
    value = [
        "bindPlanList",
        "bindSearchViewModel"
    ]
)
fun RecyclerView.bindPlanList(planList: List<PlanDto>, vm: SearchViewModel) {
    if (adapter == null) {
        val diffUtil = object : DiffUtil.ItemCallback<PlanDto>() {
            override fun areItemsTheSame(
                oldItem: PlanDto,
                newItem: PlanDto
            ): Boolean = oldItem.planId == newItem.planId

            override fun areContentsTheSame(
                oldItem: PlanDto,
                newItem: PlanDto
            ): Boolean {
                return oldItem == newItem
            }
        }

        val animator = itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        adapter = BaseListAdapter(
            layoutResId = R.layout.item_search_result_plan,
            bindingItemId = BR.item,
            viewModel = mapOf(BR.vm to vm),
            diffUtil = diffUtil
        )
    }
    bindItemList(planList)
}

@BindingAdapter(
    value = [
        "bindPlaceList",
        "bindSearchViewModel"
    ]
)
fun RecyclerView.bindPlaceList(placeList: List<PlaceDto>, vm: SearchViewModel) {
    if (adapter == null) {
        val diffUtil = object : DiffUtil.ItemCallback<PlaceDto>() {
            override fun areItemsTheSame(
                oldItem: PlaceDto,
                newItem: PlaceDto
            ): Boolean = oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: PlaceDto,
                newItem: PlaceDto
            ): Boolean {
                return oldItem == newItem
            }
        }

        val animator = itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        adapter = BaseListAdapter(
            layoutResId = R.layout.item_search_result_place,
            bindingItemId = BR.item,
            viewModel = mapOf(BR.vm to vm),
            diffUtil = diffUtil
        )
    }
    bindItemList(placeList)
}
