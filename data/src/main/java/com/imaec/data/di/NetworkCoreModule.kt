package com.imaec.data.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.imaec.data.BuildConfig
import com.imaec.data.api.Host
import com.imaec.data.api.NaverService
import com.imaec.data.api.RoadAddressService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkCoreModule {

    const val HOST_NAVER = "naverHost"
    const val HOST_JUSO = "jusoHost"

    @Provides
    @Singleton
    fun provideObjMatter(): ObjectMapper = ObjectMapper().apply {
        registerKotlinModule()
        registerModule(SimpleModule())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Provides
    @Singleton
    fun provideConverterFactory(objectMapper: ObjectMapper): JacksonConverterFactory =
        JacksonConverterFactory.create(objectMapper)

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    if (!message.startsWith("{") && !message.startsWith("[")) {
                        Timber.tag("OkHttp").d(message)
                        return
                    }

                    try {
                        Timber.tag("OkHttp").d(
                            GsonBuilder().setPrettyPrinting().create()
                                .toJson(JsonParser.parseString(message))
                        )
                    } catch (m: JsonSyntaxException) {
                        Timber.tag("OkHttp").d(message)
                    }
                }
            }
        ).apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val requestBuilder =
                        chain
                            .request()
                            .newBuilder()
                            .addHeader("X-NCP-APIGW-API-KEY-ID", "b83gl83l1i")
                            .addHeader(
                                "X-NCP-APIGW-API-KEY",
                                "XmRrqR0HLtBQj4S4FFKjOtpQrkQi5HtFalVc4dXt"
                            )
                            .addHeader("Accept", "application/json")

                    chain.proceed(requestBuilder.build())
                }
            )
            .addNetworkInterceptor(httpLoggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    @Named(HOST_NAVER)
    fun provideNaverRetrofitBuilder(
        jacksonConverterFactory: JacksonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit
        .Builder()
        .baseUrl(Host.NAVER)
        .client(okHttpClient)
        .addConverterFactory(jacksonConverterFactory)

    @Provides
    @Singleton
    fun provideNaverService(
        @Named(HOST_NAVER) retrofitBuilder: Retrofit.Builder
    ): NaverService = retrofitBuilder.build().create(NaverService::class.java)

    @Provides
    @Singleton
    @Named(HOST_JUSO)
    fun provideJusoRetrofitBuilder(
        jacksonConverterFactory: JacksonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit
        .Builder()
        .baseUrl(Host.JUSO)
        .client(okHttpClient)
        .addConverterFactory(jacksonConverterFactory)

    @Provides
    @Singleton
    fun provideRoadAddressService(
        @Named(HOST_JUSO) retrofitBuilder: Retrofit.Builder
    ): RoadAddressService = retrofitBuilder.build().create(RoadAddressService::class.java)
}
