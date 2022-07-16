package com.imaec.triplan

import android.os.Handler
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.model.CityDto
import com.imaec.domain.usecase.category.AddCategoryUseCase
import com.imaec.domain.usecase.city.AddCityUseCase
import com.imaec.domain.usecase.naverplace.GetNaverPlaceUseCase
import com.imaec.domain.usecase.place.EditPlaceUseCase
import com.imaec.domain.usecase.place.SavePlaceUseCase
import com.imaec.triplan.rule.activityscenario.DataBindingIdlingResourceRule
import com.imaec.triplan.rule.activityscenario.monitorActivity
import com.imaec.triplan.ui.writeplace.WritePlaceActivity
import com.imaec.triplan.ui.writeplace.WritePlaceState
import com.imaec.triplan.ui.writeplace.WritePlaceType
import com.imaec.triplan.ui.writeplace.WritePlaceViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WritePlaceActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var taskRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    var idlingResourceRule = DataBindingIdlingResourceRule()

    private val stateTest = MutableLiveData<WritePlaceState>()
    private val categoryTest = MutableLiveData<CategoryDto>()
    private val cityTest = MutableLiveData<CityDto>()
    private val addressTest = MutableLiveData<String>()
    private val siteTest = MutableLiveData<String>()
    private val placeNameTest = ObservableField<String>()
    private val mockkAddCategoryUseCase = mockk<AddCategoryUseCase>()
    private val mockkAddCityUseCase = mockk<AddCityUseCase>()
    private val mockkGetNaverPlaceUseCase = mockk<GetNaverPlaceUseCase>()
    private val mockkSavePlaceUseCase = mockk<SavePlaceUseCase>()
    private val mockkEditPlaceUseCase = mockk<EditPlaceUseCase>()
    private val savedStateHandle = spyk(
        SavedStateHandle(
            mapOf(
                WritePlaceActivity.PLACE to null,
                WritePlaceActivity.TYPE to WritePlaceType.WRITE
            )
        )
    )

    @BindValue
    lateinit var viewModel: WritePlaceViewModel

    @Before
    fun prepare() {
        viewModel = spyk(
            WritePlaceViewModel(
                savedStateHandle,
                mockkAddCategoryUseCase,
                mockkAddCityUseCase,
                mockkGetNaverPlaceUseCase,
                mockkSavePlaceUseCase,
                mockkEditPlaceUseCase
            )
        ) {
            every { state } returns stateTest
            every { category } returns categoryTest
            every { city } returns cityTest
            every { address } returns addressTest
            every { site } returns siteTest
            every { placeName } returns placeNameTest
        }
    }

    @Test
    fun update_category() {
        launchActivity()
        categoryTest.value = CategoryDto(0, "음식점")

        onView(withId(R.id.tv_category))
            .check(ViewAssertions.matches(withText("음식점")))
    }

    @Test
    fun click_add_category() {
        launchActivity()

        Handler(Looper.getMainLooper()).post {
            stateTest.value = WritePlaceState.OnClickAddCategory
        }
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(withText("카테고리 추가")))
        onView(withId(R.id.tv_cancel)).check(ViewAssertions.matches(withText("취소")))
        onView(withId(R.id.tv_ok)).check(ViewAssertions.matches(withText("확인")))
    }

    private fun launchActivity(): ActivityScenario<WritePlaceActivity> {
        val scenario = launch(WritePlaceActivity::class.java)
        idlingResourceRule.idlingResource.monitorActivity(scenario)

        return scenario
    }
}
