package com.imaec.triplan.ui.calendar

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.CityDto
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.databinding.ActivityCalendarBinding
import com.imaec.triplan.databinding.ViewCalendarDayBinding
import com.imaec.triplan.databinding.ViewCalendarMonthBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

@AndroidEntryPoint
class CalendarActivity : BaseActivity<ActivityCalendarBinding>(R.layout.activity_calendar) {

    private val viewModel by viewModels<CalendarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupCalendar()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupCalendar() {
        with(binding.calendar) {
            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)

                override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                    with(container.binding) {
                        vm = viewModel
                        item = month
                    }
                }
            }
            dayBinder = object : DayBinder<DayViewContainer> {
                override fun create(view: View) = DayViewContainer(view)

                override fun bind(container: DayViewContainer, day: CalendarDay) {
                    with(container.binding) {
                        vm = viewModel
                        item = day
                    }
                }
            }

            val currentMonth = YearMonth.now()
            setup(
                startMonth = currentMonth.minusMonths(12),
                endMonth = currentMonth.plusMonths(24),
                firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
            scrollToMonth(currentMonth)
        }
    }

    private fun setupListener() {
        with(binding.mtbCalendar) {
            setNavigationOnClickListener {
                finish()
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_clear -> {
                        viewModel.clearPlan()
                        binding.calendar.notifyCalendarChanged()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@CalendarActivity) {
                when (it) {
                    is CalendarState.OnClickMonth -> {
                        binding.calendar.smoothScrollToMonth(it.month.yearMonth)
                    }
                    CalendarState.OnClickDay -> binding.calendar.notifyCalendarChanged()
                    CalendarState.OnClickAdd -> finish()
                }
            }
        }
    }

    inner class MonthViewContainer(view: View) : ViewContainer(view) {
        val binding = ViewCalendarMonthBinding.bind(view)
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val binding = ViewCalendarDayBinding.bind(view)
    }

    companion object {
        const val CITY = "city"

        fun createBundle(
            city: CityDto
        ): Bundle = bundleOf(
            CITY to city
        )
    }
}
