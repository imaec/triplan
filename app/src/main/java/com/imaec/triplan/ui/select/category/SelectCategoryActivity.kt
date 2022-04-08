package com.imaec.triplan.ui.select.category

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.CategoryDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.ActivitySelectCategoryBinding
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCategoryActivity :
    BaseActivity<ActivitySelectCategoryBinding>(R.layout.activity_select_category) {

    private val viewModel by viewModels<SelectCategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupListener()
        setupData()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvSelectCategory) {
            val diffUtil = object : DiffUtil.ItemCallback<CategoryDto>() {
                override fun areItemsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean =
                    oldItem.categoryId == newItem.categoryId

                override fun areContentsTheSame(
                    oldItem: CategoryDto,
                    newItem: CategoryDto
                ): Boolean = oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(RecyclerViewDividerDecoration())

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_select_category,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupListener() {
        with(binding.mtbSelectCategory) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@SelectCategoryActivity) {
                when (it) {
                    is SelectCategoryState.OnClickCategory -> {
                        setResult(
                            RESULT_OK,
                            Intent().apply {
                                putExtra(CATEGORY, it.category)
                            }
                        )
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        const val SELECT_CATEGORY = "select_category"
        const val CATEGORY = "category"
    }
}
