package com.imaec.triplan.ui.calendar

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.imaec.triplan.R
import com.imaec.triplan.base.bindVisible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.utils.yearMonth
import java.time.DayOfWeek
import java.time.LocalDate

@BindingAdapter(
    value = [
        "bindDay",
        "bindStartDate",
        "bindEndDate",
        "bindTodayView"
    ]
)
fun TextView.bindBackground(
    day: CalendarDay?,
    startDate: LocalDate?,
    endDate: LocalDate?,
    todayView: TextView
) {
    day ?: return

    val today = LocalDate.now()
    background = null
    setTextColor(ContextCompat.getColor(context, R.color.white))
    todayView.setTextColor(ContextCompat.getColor(context, R.color.white))
    when (day.owner) {
        DayOwner.THIS_MONTH -> {
            when {
                // 첫 째날만 선택한 경우
                startDate == day.date && endDate == null -> {
                    setBackgroundResource(R.drawable.bg_selected_calendar)
                }
                // 첫 째날, 마지막 날 선택한 경우의 첫 째날 뷰
                day.date == startDate -> {
                    setBackgroundResource(R.drawable.bg_selected_calendar_start_date)
                }
                // 첫 째날, 마지막 날 선택한 경우의 첫 째날, 마지막 날은 제외한 날짜 뷰
                startDate != null &&
                    endDate != null &&
                    (day.date > startDate && day.date < endDate) -> {
                    setBackgroundResource(R.drawable.bg_selected_calendar_middle_date)
                }
                // 첫 째날, 마지막 날 선택한 경우의 마지막 날 뷰
                day.date == endDate -> {
                    setBackgroundResource(R.drawable.bg_selected_calendar_end_date)
                }
                // 오늘 날짜 뷰
                day.date == today -> {
                    setTextColor(ContextCompat.getColor(context, R.color.gray66))
                    setBackgroundResource(R.drawable.bg_selected_calendar_today)
                    todayView.setTextColor(ContextCompat.getColor(context, R.color.gray66))
                }
                else -> {
                    TextViewCompat.setTextAppearance(
                        this,
                        when (day.date.dayOfWeek) {
                            DayOfWeek.SUNDAY -> R.style.Text14BoldRed
                            DayOfWeek.SATURDAY -> R.style.Text14BoldBlue
                            else -> R.style.Text14BoldGray33
                        }
                    )
                    todayView.setTextColor(ContextCompat.getColor(context, R.color.gray33))
                }
            }
        }
        DayOwner.PREVIOUS_MONTH -> {
            TextViewCompat.setTextAppearance(this, R.style.Text14RegularGrayCC)
            if (
                startDate != null &&
                endDate != null &&
                isInDateBetween(day.date, startDate, endDate)
            ) {
                setBackgroundResource(R.drawable.bg_selected_calendar_middle_date)
                TextViewCompat.setTextAppearance(this, R.style.Text14RegularSky)
            }
        }
        DayOwner.NEXT_MONTH -> {
            TextViewCompat.setTextAppearance(this, R.style.Text14RegularGrayCC)
            if (
                startDate != null &&
                endDate != null &&
                isOutDateBetween(day.date, startDate, endDate)
            ) {
                setBackgroundResource(R.drawable.bg_selected_calendar_middle_date)
                TextViewCompat.setTextAppearance(this, R.style.Text14RegularSky)
            }
        }
    }
    todayView.bindVisible(day.date == today)
}

private fun isInDateBetween(inDate: LocalDate, startDate: LocalDate, endDate: LocalDate): Boolean {
    if (startDate.yearMonth == endDate.yearMonth) return false
    if (inDate.yearMonth == startDate.yearMonth) return true
    val firstDateInThisMonth = inDate.plusMonths(1).yearMonth.atDay(1)
    return firstDateInThisMonth >= startDate &&
        firstDateInThisMonth <= endDate &&
        startDate != firstDateInThisMonth
}

private fun isOutDateBetween(
    outDate: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate
): Boolean {
    if (startDate.yearMonth == endDate.yearMonth) return false
    if (outDate.yearMonth == endDate.yearMonth) return true
    val lastDateInThisMonth = outDate.minusMonths(1).yearMonth.atEndOfMonth()
    return lastDateInThisMonth >= startDate &&
        lastDateInThisMonth <= endDate &&
        endDate != lastDateInThisMonth
}
