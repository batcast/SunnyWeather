package com.sunnyweather.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import interfaces.heweather.com.interfacesmodule.view.HeConfig

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //和风天气账户初始化
//        HeConfig.init("HE2007312021071743","80a9894ee15448769f76d4f4d6d4e390")

        //切换至开发版服务
//        HeConfig.switchToDevService()
        //切换至商业版服务
//        HeConfig.switchToBizService()
    }
}