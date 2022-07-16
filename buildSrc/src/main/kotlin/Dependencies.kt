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

    const val desugar = "1.1.5"

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

    const val retrofit = "2.9.0"
    const val okhttp3 = "4.9.1"
    const val gson = "2.8.7"
    const val jackson_kotlin_module = "2.13.2"

    const val room = "2.4.1"

    const val junit = "4.13.1"
    const val junit_ext = "1.1.1"
    const val espresso = "3.2.0"

    const val dagger_hilt = "2.38.1"
    const val hilt = "1.0.0"

    const val firebase = "29.0.4"

    const val jsoup = "1.11.3"
    const val calendar = "1.0.4"
    const val timber = "4.7.1"

    const val aac_core = "2.1.0"
    const val junit5_version = "5.4.0"
    const val mockk = "1.12.0"
    const val coroutinesTest = "1.5.2"
    const val espresso_core = "3.3.0-rc03"
    const val espresso_intents = "3.1.0"
    const val espresso_webserver_idling_resource = "1.0.0"
    const val test_runner = "1.4.0"
    const val test_rules = "1.1.1"
    const val mock_webserver = "4.9.3"
    const val fragment_test = "1.4.0-alpha03"
    const val dex_opener = "2.0.5"
}

object Libs {
    const val desugar = "com.android.tools:desugar_jdk_libs:${Versions.desugar}"

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
    const val dagger_hilt_testing = "com.google.dagger:hilt-android-testing:${Versions.dagger_hilt}"
    const val hilt_common = "androidx.hilt:hilt-common:${Versions.hilt}"
    const val hilt_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt}"
    const val hilt_testing = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val hilt_testing_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter = "com.squareup.retrofit2:converter-jackson:${Versions.retrofit}"
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val okhttp3_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
    const val jackson_kotlin_module = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson_kotlin_module}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    // Room
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    // Jsoup
    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
    // Calendar
    const val calendar = "com.github.kizitonwose:CalendarView:${Versions.calendar}"
    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    // Testing
    const val aac_core = "androidx.arch.core:core-testing:${Versions.aac_core}"
    const val JUPITER_API = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5_version}"
    const val JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5_version}"
    const val JUPITER_PARAMS = "org.junit.jupiter:junit-jupiter-params:${Versions.junit5_version}"
    const val MOCKK = "io.mockk:mockk:${Versions.mockk}"
    const val MOCKK_ANDROID = "io.mockk:mockk-android:${Versions.mockk}"
    const val MOCKK_COMMON = "io.mockk:mockk-common:${Versions.mockk}"
    const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${Versions.espresso_core}"
    const val ESPRESSO_INTENTS = "androidx.test.espresso:espresso-intents:${Versions.espresso_intents}"
    const val ESPRESSO_MOCK_WEBSERVER_IDLING_RESOURCE =
        "com.jakewharton.espresso:okhttp3-idling-resource:${Versions.espresso_webserver_idling_resource}"
    const val TEST_RUNNER = "androidx.test:runner:${Versions.test_runner}"
    const val TEST_RULES = "androidx.test:rules:${Versions.test_rules}"
    const val MOCK_WEBSERVER = "com.squareup.okhttp3:mockwebserver:${Versions.mock_webserver}"
    const val FRAGMENT_TESTING = "androidx.fragment:fragment-testing:${Versions.fragment_test}"
    const val DEX_OPENER = "com.github.tmurakami:dexopener:${Versions.dex_opener}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val junit_ext = "androidx.test.ext:junit:${Versions.junit_ext}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}
