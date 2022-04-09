package com.imaec.triplan.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.CityDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.base.BaseMultiListAdapter
import com.imaec.triplan.base.ViewHolderType
import com.imaec.triplan.databinding.FragmentHomeBinding
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.ui.calendar.CalendarActivity
import com.imaec.triplan.ui.plan.PlanDetailActivity
import com.imaec.triplan.ui.select.city.SelectCityActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvHome) {
            val viewHolderMapper: (HomeItem) -> ViewHolderType = {
                when (it) {
                    is HomeItem.Plan -> {
                        HomeHolderType.PlanHolder
                    }
                    HomeItem.Divider -> {
                        HomeHolderType.DividerHolder
                    }
                }
            }

            val diffUtil = object : DiffUtil.ItemCallback<HomeItem>() {
                override fun areItemsTheSame(
                    oldItem: HomeItem,
                    newItem: HomeItem
                ): Boolean =
                    if (oldItem is HomeItem.Plan &&
                        newItem is HomeItem.Plan
                    ) {
                        oldItem.planType == newItem.planType
                    } else {
                        oldItem == newItem
                    }

                override fun areContentsTheSame(
                    oldItem: HomeItem,
                    newItem: HomeItem
                ): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseMultiListAdapter(
                viewHolderMapper = viewHolderMapper,
                viewHolderType = HomeHolderType::class,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(viewLifecycleOwner) {
                when (it) {
                    HomeState.OnClickWrite -> {
                        requireActivity().activityResultRegistry.register(
                            SelectCityActivity.SELECT_CITY,
                            ActivityResultContracts.StartActivityForResult()
                        ) {
                            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                                it.data?.getSerializableExtra(SelectCityActivity.CITY)?.let {
                                    startActivity<CalendarActivity>(
                                        CalendarActivity.createBundle(it as CityDto)
                                    )
                                }
                            }
                        }.launch(
                            Intent(requireContext(), SelectCityActivity::class.java)
                        )
                    }
                    is HomeState.OnClickPlan -> {
                        startActivity<PlanDetailActivity>(
                            PlanDetailActivity.createBundle(it.plan)
                        )
                    }
                }
            }
        }
    }

    enum class HomeHolderType(
        override val layoutResId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        PlanHolder(
            layoutResId = R.layout.item_home_plan_container,
            bindingItemId = BR.item
        ),
        DividerHolder(
            layoutResId = R.layout.item_divider,
            bindingItemId = BR._all
        )
    }
}
