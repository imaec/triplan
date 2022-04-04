package com.imaec.triplan.ui.writeplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.model.CityDto
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityWritePlaceBinding
import com.imaec.triplan.ui.writeplace.searchaddress.SearchAddressActivity
import com.imaec.triplan.ui.writeplace.category.SelectCategoryActivity
import com.imaec.triplan.ui.writeplace.city.SelectCityActivity
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
                        // TODO 카테고리 선택 화면
                        startActivityForResult(
                            SelectCategoryActivity.SELECT_CATEGORY,
                            SelectCategoryActivity::class.java
                        ) {
                            it.getSerializableExtra(SelectCategoryActivity.CATEGORY)?.let {
                                viewModel.setCategory(it as CategoryDto)
                            }
                        }
                    }
                    WritePlaceState.OnClickAddCategory -> {
                        // TODO 카테고리 추가 다이얼로그
                    }
                    WritePlaceState.OnClickCity -> {
                        startActivityForResult(
                            SelectCityActivity.SELECT_CITY,
                            SelectCityActivity::class.java
                        ) {
                            it.getSerializableExtra(SelectCityActivity.CITY)?.let {
                                viewModel.setCity(it as CityDto)
                            }
                        }
                    }
                    WritePlaceState.OnClickAddCity -> {
                        // TODO 지역 추가 다이얼로그
                    }
                    WritePlaceState.OnClickAddress -> {
                        startActivityForResult(
                            SearchAddressActivity.SEARCH_ADDRESS,
                            SearchAddressActivity::class.java
                        ) {
                            it.getStringExtra(SearchAddressActivity.ADDRESS)?.let {
                                viewModel.setAddress(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startActivityForResult(
        key: String,
        activity: Class<*>,
        onResultOk: (Intent) -> Unit
    ) {
        activityResultRegistry.register(
            key,
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                it.data?.let(onResultOk)
            }
        }.launch(Intent(this, activity))
    }
}
