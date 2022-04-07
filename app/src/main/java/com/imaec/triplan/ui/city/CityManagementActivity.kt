package com.imaec.triplan.ui.city

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseMultiListAdapter
import com.imaec.triplan.base.ViewHolderType
import com.imaec.triplan.databinding.ActivityCityManagementBinding
import com.imaec.triplan.ext.hideKeyboard
import com.imaec.triplan.ext.toast
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import com.imaec.triplan.ui.common.InputDialog
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
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvCity) {
            val viewHolderMapper: (CityManagementItem) -> ViewHolderType = {
                when (it) {
                    CityManagementItem.CityInput -> {
                        CityManagementHolderType.InputHolder
                    }
                    is CityManagementItem.City -> {
                        CityManagementHolderType.CityHolder
                    }
                }
            }

            val diffUtil = object : DiffUtil.ItemCallback<CityManagementItem>() {
                override fun areItemsTheSame(
                    oldItem: CityManagementItem,
                    newItem: CityManagementItem
                ): Boolean =
                    if (oldItem is CityManagementItem.City &&
                        newItem is CityManagementItem.City
                    ) {
                        oldItem.city.cityId == newItem.city.cityId
                    } else {
                        oldItem == newItem
                    }

                override fun areContentsTheSame(
                    oldItem: CityManagementItem,
                    newItem: CityManagementItem
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
                viewHolderType = CityManagementHolderType::class,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupListener() {
        with(binding.mtbCityManagement) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@CityManagementActivity) {
                when (it) {
                    is CityManagementState.OnError -> toast(it.message)
                    CityManagementState.OnClickAdd -> hideKeyboard(binding.root)
                    is CityManagementState.OnClickCity -> {
                        InputDialog(
                            context = this@CityManagementActivity,
                            title = "지역 수정",
                            hint = "지역을 입력하세요.",
                            text = it.city.city,
                            okCallback = { city ->
                                viewModel.updateCity(it.city.cityId, city)
                            }
                        ).show()
                    }
                }
            }
        }
    }

    enum class CityManagementHolderType(
        override val layoutResId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        InputHolder(
            layoutResId = R.layout.item_city_input,
            bindingItemId = BR._all
        ),
        CityHolder(
            layoutResId = R.layout.item_city_management,
            bindingItemId = BR.item
        )
    }
}
