package it.macgood.hotelsapp.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.activity.isConnectedToInternet
import it.macgood.core.activity.restartApp
import it.macgood.core.databinding.LayoutNoInternetConnectionBinding
import it.macgood.core.extension.viewBinding
import it.macgood.hotelsapp.R
import it.macgood.core.R as CoreResource
import it.macgood.hotelsapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isConnectedToInternet()) {

            setContentView(binding.root)

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
            navController = navHostFragment.navController
            setupActionBarWithNavController(navController)

            MapKitFactory.initialize(this)

        } else {
            val binding = LayoutNoInternetConnectionBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.restartAppButton.setOnClickListener {
                restartApp(intent)
            }

            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}