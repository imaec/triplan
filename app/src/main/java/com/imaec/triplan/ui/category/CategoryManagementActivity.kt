package com.imaec.triplan.ui.category

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseMultiListAdapter
import com.imaec.triplan.base.ViewHolderType
import com.imaec.triplan.databinding.ActivityCategoryManagementBinding
import com.imaec.triplan.ext.hideKeyboard
import com.imaec.triplan.ext.toast
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import com.imaec.triplan.ui.common.InputDialog
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
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvCategory) {
            val viewHolderMapper: (CategoryManagementItem) -> ViewHolderType = {
                when (it) {
                    CategoryManagementItem.CategoryInput -> {
                        CategoryManagementHolderType.InputHolder
                    }
                    is CategoryManagementItem.Category -> {
                        CategoryManagementHolderType.CategoryHolder
                    }
                }
            }

            val diffUtil = object : DiffUtil.ItemCallback<CategoryManagementItem>() {
                override fun areItemsTheSame(
                    oldItem: CategoryManagementItem,
                    newItem: CategoryManagementItem
                ): Boolean =
                    if (oldItem is CategoryManagementItem.Category &&
                        newItem is CategoryManagementItem.Category
                    ) {
                        oldItem.category.categoryId == newItem.category.categoryId
                    } else {
                        oldItem == newItem
                    }

                override fun areContentsTheSame(
                    oldItem: CategoryManagementItem,
                    newItem: CategoryManagementItem
                ): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(RecyclerViewDividerDecoration(startPosition = 1))

            adapter = BaseMultiListAdapter(
                viewHolderMapper = viewHolderMapper,
                viewHolderType = CategoryManagementHolderType::class,
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
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@CategoryManagementActivity) {
                when (it) {
                    is CategoryManagementState.OnError -> toast(it.message)
                    CategoryManagementState.OnClickAdd -> hideKeyboard(binding.root)
                    is CategoryManagementState.OnClickCategory -> {
                        InputDialog(
                            context = this@CategoryManagementActivity,
                            title = "카테고리 수정",
                            hint = "카테고리를 입력하세요.",
                            text = it.category.category,
                            okCallback = { category ->
                                viewModel.updateCategory(it.category.categoryId, category)
                            }
                        ).show()
                    }
                }
            }
        }
    }

    enum class CategoryManagementHolderType(
        override val layoutResId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        InputHolder(
            layoutResId = R.layout.item_category_input,
            bindingItemId = BR._all
        ),
        CategoryHolder(
            layoutResId = R.layout.item_category_management,
            bindingItemId = BR.item
        )
    }
}
