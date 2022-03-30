object Apps {
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val build_tools = "31.0.0"
}

object Versions {
    const val java_version = "1.8"

    const val google_services = "4.3.10"
    const val ktlint = "10.0.0"

    const val gradle = "4.0.1"
    const val kotlin = "1.6.10"
    const val appcompat = "1.4.1"
    const val core_ktx = "1.7.0"
    const val constraint_layout = "2.1.3"
    const val material = "1.6.0-alpha02"
    const val activity_ktx = "1.4.0"
    const val fragment_ktx = "1.4.1"

    const val lifecycle = "2.4.1"
    const val lifecycle_extension = "2.2.0"

    const val coroutine = "1.5.2"

    const val room = "2.4.1"

    const val junit = "4.13.1"
    const val junit_ext = "1.1.1"
    const val espresso = "3.2.0"

    const val dagger_hilt = "2.38.1"
    const val hilt = "1.0.0"

    const val firebase = "29.0.4"

    const val jsoup = "1.11.3"
    const val timber = "4.7.1"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    // Lifecycle
    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_extension}"
    // Coroutine
    const val coroutine_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val coroutine_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    // Hilt
    const val dagger_hilt_android = "com.google.dagger:hilt-android:${Versions.dagger_hilt}"
    const val dagger_hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger_hilt}"
    const val hilt_common = "androidx.hilt:hilt-common:${Versions.hilt}"
    const val hilt_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt}"
    // Room
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    // Jsoup
    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val junit_ext = "androidx.test.ext:junit:${Versions.junit_ext}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}
