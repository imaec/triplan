package com.imaec.triplan.ui.plan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.imaec.domain.model.PlanDayDto
import com.imaec.domain.model.PlanDto
import com.imaec.triplan.ui.plan.list.PlanListFragment

class PlanDetailPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val plan: PlanDto?,
    private val planList: List<PlanDayDto>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = planList.size

    override fun createFragment(position: Int): Fragment {
        return PlanListFragment.instance(plan, planList[position])
    }
}
