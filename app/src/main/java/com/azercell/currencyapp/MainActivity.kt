package com.azercell.currencyapp

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.azercell.coreui.managers.WorkerManager
import com.azercell.currencyapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), WorkerManager {

    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted->
        if(isGranted){
            startRateGuardWorker()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        setContentView(binding!!.root)

        initViews()

        initPermission()
    }

    private fun initViews() {
        navController = (supportFragmentManager.findFragmentById(
            R.id.activityContainerView
        ) as NavHostFragment).navController

        binding?.bottomNavigationView?.setupWithNavController(navController!!)
    }

    private fun initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                POST_NOTIFICATIONS
            )
        }else{
            startRateGuardWorker()
        }
    }

    override fun startRateGuardWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(
            NetworkType.CONNECTED
        ).build()

        val rateGuardWork = PeriodicWorkRequestBuilder<RateGuardWorker>(
            15, TimeUnit.MINUTES
        ).setConstraints(constraints).setBackoffCriteria(
            BackoffPolicy.LINEAR,
            15, TimeUnit.MINUTES
        ).build()

        val workManagerInstance = WorkManager.getInstance(this)

        workManagerInstance.enqueueUniquePeriodicWork(
            RateGuardWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            rateGuardWork
        )
    }
}