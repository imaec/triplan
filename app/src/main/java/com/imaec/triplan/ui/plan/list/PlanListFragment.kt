package com.imaec.triplan.ui.plan.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.PlanDayDto
import com.imaec.domain.model.PlanDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.base.BaseMultiListAdapter
import com.imaec.triplan.base.ViewHolderType
import com.imaec.triplan.databinding.FragmentPlanListBinding
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.ui.plan.write.PlanWriteActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanListFragment : BaseFragment<FragmentPlanListBinding>(R.layout.fragment_plan_list) {

    private val viewModel by viewModels<PlanListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvPlanList) {
            val viewHolderMapper: (PlanListItem) -> ViewHolderType = {
                when (it) {
                    is PlanListItem.PlanItem -> {
                        PlanListHolderType.PlanItemHolder
                    }
                    PlanListItem.AddButton -> {
                        PlanListHolderType.AddButtonHolder
                    }
                }
            }

            val diffUtil = object : DiffUtil.ItemCallback<PlanListItem>() {
                override fun areItemsTheSame(
                    oldItem: PlanListItem,
                    newItem: PlanListItem
                ): Boolean =
                    if (oldItem is PlanListItem.PlanItem &&
                        newItem is PlanListItem.PlanItem
                    ) {
                        oldItem.itemNo == oldItem.itemNo
                    } else {
                        oldItem == newItem
                    }

                override fun areContentsTheSame(
                    oldItem: PlanListItem,
                    newItem: PlanListItem
                ): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseMultiListAdapter(
                viewHolderMapper = viewHolderMapper,
                viewHolderType = PlanListHolderType::class,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(viewLifecycleOwner) {
                when (it) {
                    is PlanListState.OnClickAdd -> {
                        startActivity<PlanWriteActivity>(
                            PlanWriteActivity.createBundle(
                                plan = viewModel.plan,
                                planDay = viewModel.planDay.value?.planDay ?: 0
                            )
                        )
                    }
                    is PlanListState.OnClickPlanItem -> {
                        startActivity<PlanWriteActivity>(
                            PlanWriteActivity.createBundle(
                                plan = viewModel.plan,
                                planDay = viewModel.planDay.value?.planDay ?: 0,
                                planItem = it.planItem
                            )
                        )
                    }
                }
            }
        }
    }

    enum class PlanListHolderType(
        override val layoutResId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        PlanItemHolder(
            layoutResId = R.layout.item_plan_list,
            bindingItemId = BR.item
        ),
        AddButtonHolder(
            layoutResId = R.layout.item_plan_add_button,
            bindingItemId = BR._all
        )
    }

    companion object {
        const val PLAN = "plan"
        const val PLAN_DAY = "plan_day"

        fun instance(plan: PlanDto?, planDay: PlanDayDto) = PlanListFragment().apply {
            arguments = bundleOf(
                PLAN to plan,
                PLAN_DAY to planDay
            )
        }
    }
}
