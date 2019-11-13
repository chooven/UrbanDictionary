package net.chooven.urbandictionary

import android.app.Application
import net.chooven.urbandictionary.data.ApiServicesProvider
import timber.log.Timber

class UrbanDictionary: Application() {
    companion object {
        lateinit var apiServicesProvider: ApiServicesProvider
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        apiServicesProvider = ApiServicesProvider(this)
    }
}