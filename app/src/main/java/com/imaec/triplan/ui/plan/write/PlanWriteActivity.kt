package com.imaec.triplan.ui.plan.write

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.model.CityDto
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.model.PlanDto
import com.imaec.domain.model.PlanItemDto
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityPlanWriteBinding
import com.imaec.triplan.ext.toast
import com.imaec.triplan.ui.common.InputDialog
import com.imaec.triplan.ui.select.category.SelectCategoryActivity
import com.imaec.triplan.ui.select.city.SelectCityActivity
import com.imaec.triplan.ui.select.place.SelectPlaceActivity
import com.imaec.triplan.ui.writeplace.WritePlaceActivity
import com.imaec.triplan.ui.writeplace.searchaddress.SearchAddressActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanWriteActivity : BaseActivity<ActivityPlanWriteBinding>(R.layout.activity_plan_write) {

    private val viewModel by viewModels<PlanWriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupListener() {
        with(binding.mtbPlanWrite) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@PlanWriteActivity) {
                when (it) {
                    PlanWriteState.OnClickCategory -> {
                        startActivityForResult(
                            key = SelectCategoryActivity.SELECT_CATEGORY,
                            activity = SelectCategoryActivity::class.java,
                            onResultOk = {
                                it.getSerializableExtra(SelectCategoryActivity.CATEGORY)?.let {
                                    viewModel.setCategory((it as CategoryDto).category)
                                }
                            }
                        )
                    }
                    PlanWriteState.OnClickAddCategory -> {
                        InputDialog(
                            context = this@PlanWriteActivity,
                            title = "카테고리 추가",
                            hint = "카테고리를 입력하세요.",
                            okCallback = { city ->
                                viewModel.setCategory(city)
                            }
                        ).show()
                    }
                    PlanWriteState.OnClickCity -> {
                        startActivityForResult(
                            key = SelectCityActivity.SELECT_CITY,
                            activity = SelectCityActivity::class.java,
                            onResultOk = {
                                it.getSerializableExtra(SelectCityActivity.CITY)?.let {
                                    viewModel.setCity((it as CityDto).city)
                                }
                            }
                        )
                    }
                    PlanWriteState.OnClickAddCity -> {
                        InputDialog(
                            context = this@PlanWriteActivity,
                            title = "지역 추가",
                            hint = "지역을 입력하세요.",
                            okCallback = { city ->
                                viewModel.setCity(city)
                            }
                        ).show()
                    }
                    PlanWriteState.OnClickPlace -> {
                        startActivityForResult(
                            key = SelectPlaceActivity.SELECT_PLACE,
                            activity = SelectPlaceActivity::class.java,
                            onResultOk = {
                                it.getSerializableExtra(SelectPlaceActivity.PLACE)?.let {
                                    viewModel.setPlace(it as PlaceDto)
                                }
                            }
                        )
                    }
                    PlanWriteState.OnClickAddPlace -> {
                        startActivityForResult(
                            key = WritePlaceActivity.WRITE_PLACE,
                            activity = WritePlaceActivity::class.java,
                            onResultOk = {
                                it.getSerializableExtra(WritePlaceActivity.PLACE)?.let {
                                    viewModel.setPlace(it as PlaceDto)
                                }
                            }
                        )
                    }
                    PlanWriteState.OnClickAddress -> {
                        startActivityForResult(
                            key = SearchAddressActivity.SEARCH_ADDRESS,
                            activity = SearchAddressActivity::class.java,
                            bundle = SearchAddressActivity.createBundle(
                                viewModel.getAddressNotDefault()
                            ),
                            onResultOk = {
                                it.getStringExtra(SearchAddressActivity.ADDRESS)?.let {
                                    viewModel.setAddress(it)
                                }
                            }
                        )
                    }
                    PlanWriteState.OnClickSave -> {
                        finish()
                    }
                    is PlanWriteState.OnError -> toast(it.message)
                }
            }
        }
    }

    private fun startActivityForResult(
        key: String,
        activity: Class<*>,
        onResultOk: (Intent) -> Unit,
        bundle: Bundle = bundleOf()
    ) {
        activityResultRegistry.register(
            key,
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                it.data?.let(onResultOk)
            }
        }.launch(
            Intent(this, activity).apply {
                putExtras(bundle)
            }
        )
    }

    companion object {
        const val PLAN = "plan"
        const val PLAN_DAY = "plan_day"
        const val PLAN_ITEM = "plan_item"

        fun createBundle(
            plan: PlanDto?,
            planDay: Int,
            planItem: PlanItemDto? = null
        ): Bundle = bundleOf(
            PLAN to plan,
            PLAN_DAY to planDay,
            PLAN_ITEM to planItem
        )
    }
}
