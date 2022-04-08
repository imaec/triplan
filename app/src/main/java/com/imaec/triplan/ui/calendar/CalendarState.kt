package com.imaec.triplan.ui.calendar

import com.kizitonwose.calendarview.model.CalendarMonth

sealed class CalendarState {

    data class OnClickMonth(val month: CalendarMonth) : CalendarState()
    object OnClickDay : CalendarState()
}
