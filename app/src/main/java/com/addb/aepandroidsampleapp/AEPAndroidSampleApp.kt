package com.addb.aepandroidsampleapp

import android.app.Application
import com.adobe.marketing.mobile.Assurance
import com.adobe.marketing.mobile.LoggingMode
import com.adobe.marketing.mobile.MobileCore
import com.adobe.marketing.mobile.Analytics
import com.adobe.marketing.mobile.Identity
import com.adobe.marketing.mobile.Audience
import com.adobe.marketing.mobile.services.Log

class AEPAndroidSampleApp : Application() {
    // TODO: Set up the preferred Environment File ID from your mobile property configured in Data Collection UI
    private var ENVIRONMENT_FILE_ID: String = ""

    override fun onCreate() {
        super.onCreate()

        MobileCore.setLogLevel(LoggingMode.VERBOSE)
        MobileCore.setApplication(this)

        MobileCore.registerExtensions(listOf(Analytics.EXTENSION, Identity.EXTENSION, Audience.EXTENSION, Assurance.EXTENSION)) {
            Log.trace("AEPAndroidSampleApp", "MobileCore.registerExtensionsCallback","AEP Mobile SDK initialized!")
            MobileCore.configureWithAppID(ENVIRONMENT_FILE_ID)
        }
    }
}
