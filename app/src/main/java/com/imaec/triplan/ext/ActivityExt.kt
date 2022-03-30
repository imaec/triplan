package com.imaec.triplan.ext

import android.app.Activity
import android.os.Bundle

inline fun <reified T : Activity> Activity.startActivityWithFinish(bundle: Bundle? = null) {
    startActivity<T>(bundle)
    finish()
}
