package com.jaknaeso.app

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}
