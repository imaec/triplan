package com.imaec.triplan.ui.city

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.ActivityCityManagementBinding
import com.imaec.triplan.model.CityVo
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityManagementActivity :
    BaseActivity<ActivityCityManagementBinding>(R.layout.activity_city_management) {

    private val viewModel by viewModels<CityManagementViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupListener()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvSetting) {
            val diffUtil = object : DiffUtil.ItemCallback<CityVo>() {
                override fun areItemsTheSame(oldItem: CityVo, newItem: CityVo): Boolean =
                    oldItem.cityId == newItem.cityId

                override fun areContentsTheSame(oldItem: CityVo, newItem: CityVo): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(RecyclerViewDividerDecoration())

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_city_management,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupListener() {
        with(binding.mtbCategoryManagement) {
            setNavigationOnClickListener {
                finish()
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_add -> {
                        // TODO 지역 추가
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
