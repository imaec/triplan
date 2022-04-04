package com.imaec.triplan.ui.writeplace.city

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.CityDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.ActivitySelectCityBinding
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCityActivity :
    BaseActivity<ActivitySelectCityBinding>(R.layout.activity_select_city) {

    private val viewModel by viewModels<SelectCityViewModel>()

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
        with(binding.rvSelectCity) {
            val diffUtil = object : DiffUtil.ItemCallback<CityDto>() {
                override fun areItemsTheSame(oldItem: CityDto, newItem: CityDto): Boolean =
                    oldItem.cityId == newItem.cityId

                override fun areContentsTheSame(oldItem: CityDto, newItem: CityDto): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(RecyclerViewDividerDecoration())

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_select_city,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupListener() {
        with(binding.mtbSelectCity) {
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
            observe(this@SelectCityActivity) {
                when (it) {
                    is SelectCityState.OnClickCity -> {
                        setResult(
                            RESULT_OK,
                            Intent().apply {
                                putExtra(CITY, it.city)
                            }
                        )
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        const val SELECT_CITY = "select_city"
        const val CITY = "city"
    }
}
