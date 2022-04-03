package com.imaec.triplan.ui.main.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.FragmentSettingBinding
import com.imaec.triplan.ext.getVersion
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.model.SettingVo
import com.imaec.triplan.ui.category.CategoryManagementActivity
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel by viewModels<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupCollect()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvSetting) {
            val diffUtil = object : DiffUtil.ItemCallback<SettingVo>() {
                override fun areItemsTheSame(oldItem: SettingVo, newItem: SettingVo): Boolean =
                    oldItem.title == newItem.title

                override fun areContentsTheSame(oldItem: SettingVo, newItem: SettingVo): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(RecyclerViewDividerDecoration())

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_setting,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.fetchData(getVersion())
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    SettingEvent.OnClickPlaceManagement -> {
                        startActivity<CategoryManagementActivity>()
                    }
                    SettingEvent.OnClickCityManagement -> {
                    }
                    SettingEvent.OnClickShare -> {
                    }
                    else -> {}
                }
            }
        }
    }
}
