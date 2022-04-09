package com.imaec.triplan.ui.plan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.viewpager2.widget.ViewPager2
import com.imaec.domain.model.PlanDto
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityPlanDetailBinding
import com.imaec.triplan.ui.common.CommonDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanDetailActivity : BaseActivity<ActivityPlanDetailBinding>(R.layout.activity_plan_detail) {

    private val viewModel by viewModels<PlanDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupViewPager()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupViewPager() {
        with(binding) {
            vpPlanDetail.apply {
                adapter = PlanDetailPagerAdapter(
                    this@PlanDetailActivity,
                    plan = viewModel.plan,
                    planList = viewModel.plan?.planDayList ?: emptyList()
                )
                offscreenPageLimit = viewModel.plan?.planDayList?.size ?: 0
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            mtbPlanDetail.setNavigationOnClickListener {
                finish()
            }
            mtbPlanDetail.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_delete -> {
                        CommonDialog(
                            context = this@PlanDetailActivity,
                            title = "내 일정 삭제",
                            text = "\"${viewModel.plan?.planName}\"을(를) 삭제하시겠습니까?",
                            okCallback = {
                                viewModel.deletePlan()
                            }
                        ).show()
                        true
                    }
                    else -> false
                }
            }

            vpPlanDetail.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.setCurrentPage(position)
                }
            })
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@PlanDetailActivity) {
                when (it) {
                    PlanDetailState.OnClickLeft -> {
                        binding.vpPlanDetail.setCurrentItem(
                            binding.vpPlanDetail.currentItem - 1,
                            true
                        )
                    }
                    PlanDetailState.OnClickRight -> {
                        binding.vpPlanDetail.setCurrentItem(
                            binding.vpPlanDetail.currentItem + 1,
                            true
                        )
                    }
                    PlanDetailState.DeletedPlan -> {
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        const val PLAN = "plan"

        fun createBundle(
            plan: PlanDto
        ): Bundle = bundleOf(
            PLAN to plan
        )
    }
}
