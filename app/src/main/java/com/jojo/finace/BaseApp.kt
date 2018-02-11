package com.jojo.finace

import android.app.Application
import com.jojo.finace.constants.Constants
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary

/**
 * Package Name com.jojo.finace
 * Project Name finance
 * Created by JOJO on 18/2/11 11:17
 * Class Name BaseApp
 * Remarks:
 */
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(Constants.BASE_TAG)
                .build()
        val adapter: AndroidLogAdapter = object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return Constants.IS_DEBUG
            }
        }
        Logger.addLogAdapter(adapter)
    }
}