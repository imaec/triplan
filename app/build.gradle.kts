plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdk = Apps.compileSdk
    buildToolsVersion = Apps.build_tools
    defaultConfig {
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = Apps.versionCode
        versionName = Apps.versionName

        testInstrumentationRunner = "com.imaec.triplan.DexOpeningTestRunner"
        multiDexEnabled = true
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources {
            excludes.add("META-INF/LICENSE*")
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
            excludes.add("META-INF/licenses/**")
            pickFirsts.add("win32-x86-64/attach_hotspot_windows.dll")
            pickFirsts.add("win32-x86/attach_hotspot_windows.dll")
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    coreLibraryDesugaring(Libs.desugar)

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    testImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.junit_ext)
    androidTestImplementation(TestLibs.espresso)
    androidTestImplementation(Libs.aac_core)
    testImplementation(Libs.aac_core)
    testImplementation(Libs.JUPITER_API)
    testImplementation(Libs.JUPITER_ENGINE)
    testImplementation(Libs.JUPITER_PARAMS)
    testImplementation(Libs.MOCKK)
    testImplementation(Libs.MOCKK_COMMON)
    androidTestImplementation(Libs.COROUTINE_TEST)
    androidTestImplementation(Libs.MOCKK_ANDROID)
    androidTestImplementation(Libs.ESPRESSO_CORE)
    androidTestImplementation(Libs.ESPRESSO_CONTRIB)
    androidTestImplementation(Libs.ESPRESSO_INTENTS)
    androidTestImplementation(Libs.ESPRESSO_MOCK_WEBSERVER_IDLING_RESOURCE)
    androidTestImplementation(Libs.TEST_RUNNER)
    androidTestImplementation(Libs.TEST_RULES)
    androidTestImplementation(Libs.MOCK_WEBSERVER)
    androidTestImplementation(Libs.DEX_OPENER)

    implementation(Libs.kotlin)
    implementation(Libs.appcompat)
    implementation(Libs.core_ktx)
    implementation(Libs.material)
    implementation(Libs.constraint_layout)
    implementation(Libs.activity_ktx)
    implementation(Libs.fragment_ktx)
    implementation(Libs.FRAGMENT_TESTING)

    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_extensions)

    implementation(Libs.coroutine_core)
    implementation(Libs.coroutine_android)

    implementation(Libs.dagger_hilt_android)
    kapt(Libs.dagger_hilt_compiler)
    androidTestImplementation(Libs.dagger_hilt_testing)
    implementation(Libs.hilt_common)
    kapt(Libs.hilt_compiler)
    androidTestImplementation(Libs.hilt_testing)
    kaptAndroidTest(Libs.hilt_testing_compiler)

    implementation(Libs.retrofit)
    implementation(Libs.retrofit_converter)
    implementation(Libs.okhttp3)
    implementation(Libs.okhttp3_interceptor)
    implementation(Libs.jackson_kotlin_module)
    implementation(Libs.gson)

    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    kapt(Libs.room_compiler)

    implementation(Libs.jsoup)
    implementation(Libs.calendar)
    implementation(Libs.timber)
}

ktlint {
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
