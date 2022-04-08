package com.imaec.triplan.ext

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

fun <T> MediatorLiveData<T>.addSourceList(vararg liveDatas: LiveData<*>, onChanged: () -> T) {
    liveDatas.forEach {
        this.addSource(it) { value = onChanged() }
    }
}
