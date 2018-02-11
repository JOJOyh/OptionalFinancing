package com.jojo.finace

import android.app.Application
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
    }
}