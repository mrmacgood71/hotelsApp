package it.macgood.hotelsapp

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import it.macgood.hotelsapp.presentation.utils.Constants.MAP_FACTORY_API_KEY

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(MAP_FACTORY_API_KEY)
    }
}