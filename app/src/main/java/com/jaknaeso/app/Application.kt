package com.jaknaeso.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
