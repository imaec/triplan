package com.imaec.triplan.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast

inline fun <reified T : Activity> Context.startActivity(
    bundle: Bundle? = null,
    transitionBundle: Bundle? = null
) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent, transitionBundle)
}

fun Context.toast(msg: CharSequence) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(strRes: Int) {
    Toast.makeText(this, strRes, Toast.LENGTH_SHORT).show()
}

fun Context.getVersion(): String {
    var version = "Unknown"
    val packageInfo: PackageInfo

    try {
        packageInfo = applicationContext
            .packageManager
            .getPackageInfo(applicationContext.packageName, 0)
        version = packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        return version
    }
    return version
}
