package com.imaec.triplan.ext

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Fragment.startActivity(
    bundle: Bundle? = null,
    transitionBundle: Bundle? = null
) {
    requireContext().startActivity<T>(bundle, transitionBundle)
}

inline fun <reified T : Activity> Fragment.startActivityWithFinish(bundle: Bundle? = null) {
    requireActivity().startActivityWithFinish<T>(bundle)
}

fun Fragment.toast(msg: CharSequence) {
    requireContext().toast(msg)
}

fun Fragment.toast(strRes: Int) {
    requireContext().toast(strRes)
}

fun Fragment.getVersion(): String = requireContext().getVersion()

fun Fragment.showKeyboard(view: View, delay: Long = 100) {
    requireContext().showKeyboard(view, delay)
}

fun Fragment.hideKeyboard(view: View, delay: Long = 100) {
    requireContext().hideKeyboard(view, delay)
}
