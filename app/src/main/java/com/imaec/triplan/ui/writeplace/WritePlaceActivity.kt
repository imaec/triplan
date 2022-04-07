package com.imaec.triplan.ui.writeplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.model.CityDto
import com.imaec.domain.model.NaverPlaceDto
import com.imaec.domain.model.PlaceDto
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityWritePlaceBinding
import com.imaec.triplan.ext.toast
import com.imaec.triplan.ui.common.CommonBottomListener
import com.imaec.triplan.ui.common.InputDialog
import com.imaec.triplan.ui.writeplace.searchaddress.SearchAddressActivity
import com.imaec.triplan.ui.writeplace.category.SelectCategoryActivity
import com.imaec.triplan.ui.writeplace.city.SelectCityActivity
import com.imaec.triplan.ui.writeplace.naverplace.SelectNaverPlaceBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritePlaceActivity : BaseActivity<ActivityWritePlaceBinding>(R.layout.activity_write_place) {

    private val viewModel by viewModels<WritePlaceViewModel>()

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
        with(binding.mtbWritePlace) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@WritePlaceActivity) {
                when (it) {
                    WritePlaceState.OnClickCategory -> {
                        startActivityForResult(
                            key = SelectCategoryActivity.SELECT_CATEGORY,
                            activity = SelectCategoryActivity::class.java,
                            onResultOk = {
                                it.getSerializableExtra(SelectCategoryActivity.CATEGORY)?.let {
                                    viewModel.setCategory(it as CategoryDto)
                                }
                            }
                        )
                    }
                    WritePlaceState.OnClickAddCategory -> {
                        InputDialog(
                            context = this@WritePlaceActivity,
                            title = "카테고리 추가",
                            hint = "카테고리를 입력하세요.",
                            okCallback = { city ->
                                viewModel.saveCategory(city)
                            }
                        ).show()
                    }
                    WritePlaceState.OnClickCity -> {
                        startActivityForResult(
                            key = SelectCityActivity.SELECT_CITY,
                            activity = SelectCityActivity::class.java,
                            onResultOk = {
                                it.getSerializableExtra(SelectCityActivity.CITY)?.let {
                                    viewModel.setCity(it as CityDto)
                                }
                            }
                        )
                    }
                    WritePlaceState.OnClickAddCity -> {
                        InputDialog(
                            context = this@WritePlaceActivity,
                            title = "지역 추가",
                            hint = "지역을 입력하세요.",
                            okCallback = { city ->
                                viewModel.saveCity(city)
                            }
                        ).show()
                    }
                    WritePlaceState.OnClickAddress -> {
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
                    is WritePlaceState.OnLoadNaverPlace -> {
                        SelectNaverPlaceBottomSheet.instance(
                            manager = supportFragmentManager,
                            list = it.list,
                            listener = object : CommonBottomListener<NaverPlaceDto> {
                                override fun onBottomSelected(data: NaverPlaceDto) {
                                    viewModel.setPlace(
                                        data.copy(
                                            title = HtmlCompat.fromHtml(
                                                data.title,
                                                HtmlCompat.FROM_HTML_MODE_LEGACY
                                            ).toString()
                                        )
                                    )
                                }
                            }
                        )
                    }
                    WritePlaceState.OnSuccess -> {
                        finish()
                    }
                    is WritePlaceState.OnError -> toast(it.message)
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
        const val PLACE = "place"
        const val TYPE = "type"

        fun createBundle(
            place: PlaceDto,
            type: WritePlaceType
        ): Bundle = bundleOf(
            PLACE to place,
            TYPE to type
        )
    }
}
