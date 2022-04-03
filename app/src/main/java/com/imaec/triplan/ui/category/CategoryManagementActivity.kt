package com.imaec.triplan.ui.category

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.ActivityCategoryManagementBinding
import com.imaec.triplan.model.CategoryVo
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryManagementActivity :
    BaseActivity<ActivityCategoryManagementBinding>(R.layout.activity_category_management) {

    private val viewModel by viewModels<CategoryManagementViewModel>()

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
            val diffUtil = object : DiffUtil.ItemCallback<CategoryVo>() {
                override fun areItemsTheSame(oldItem: CategoryVo, newItem: CategoryVo): Boolean =
                    oldItem.categoryId == newItem.categoryId

                override fun areContentsTheSame(oldItem: CategoryVo, newItem: CategoryVo): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(RecyclerViewDividerDecoration())

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_category_management,
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
                        // TODO 카테고리 추가
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
