package com.example.kakao_project;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this,"421a203c0e6f520768fa77521184017e");
    }
}
