package com.imaec.triplan.ui.plan.more

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.PlanDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.ActivityPlanMoreBinding
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.ui.plan.PlanDetailActivity
import com.imaec.triplan.ui.plan.PlanType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanMoreActivity : BaseActivity<ActivityPlanMoreBinding>(R.layout.activity_plan_more) {

    private val viewModel by viewModels<PlanMoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvPlanMore) {
            val diffUtil = object : DiffUtil.ItemCallback<PlanDto>() {
                override fun areItemsTheSame(oldItem: PlanDto, newItem: PlanDto): Boolean =
                    oldItem.planId == newItem.planId

                override fun areContentsTheSame(oldItem: PlanDto, newItem: PlanDto): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_plan_more,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupListener() {
        with(binding.mtbPlanMore) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@PlanMoreActivity) {
                when (it) {
                    is PlanMoreState.OnClickPlan -> {
                        startActivity<PlanDetailActivity>(
                            PlanDetailActivity.createBundle(it.plan)
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val TITLE = "title"
        const val PLAN_TYPE = "plan_type"

        fun createBundle(
            title: String,
            planType: PlanType
        ): Bundle = bundleOf(
            TITLE to title,
            PLAN_TYPE to planType
        )
    }
}
