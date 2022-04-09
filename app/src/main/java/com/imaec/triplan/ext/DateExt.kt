package com.imaec.triplan.ext

import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val DATE_PATTERN_yyyy_MM_dd_E = "yyyy.MM.dd(E)"
const val DATE_PATTERN_MM_DD_E = "MM.dd (E)"

private fun dtf(pattern: String) = DateTimeFormatter.ofPattern(pattern)

fun LocalDate?.dateToStringFormat(pattern: String): String = dtf(pattern).format(this)

fun Long.dateToStringFormat(pattern: String): String =
    dtf(pattern).format(LocalDate.ofEpochDay(this))
