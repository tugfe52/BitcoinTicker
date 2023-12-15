package com.development.bitcointicker.view.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.development.bitcointicker.R
import com.development.bitcointicker.databinding.ActivityHomeBinding
import com.development.bitcointicker.utils.worker.MyWorkerImpl
import com.development.bitcointicker.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private val homeVm: HomeVM by viewModels()
    override fun mViewModel(): ViewModel = homeVm

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            initWorker()
        } else {
            // permission denied or forever denied
        }
    }

    override fun setupActivity() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)
        checkNotificationPermission()
    }

    companion object {
        const val REPEAT_INTERVAL = 15L
    }

    private fun initWorker() {

        WorkManager.getInstance(this).apply {
            val periodicRequest =
                PeriodicWorkRequest.Builder(
                    MyWorkerImpl::class.java,
                    REPEAT_INTERVAL,
                    TimeUnit.MINUTES
                )
                    .build()

            this.enqueue(periodicRequest)

            this.getWorkInfoByIdLiveData(periodicRequest.id)
                .observe(this@HomeActivity) { workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        Log.e("WorkerState", "Work Finish")
                    } else {
                        Log.e("WorkerState", "Work Error")
                    }
                }
        }
    }

    private fun checkNotificationPermission(){
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            initWorker()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }
}