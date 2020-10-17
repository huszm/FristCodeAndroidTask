package com.fristcode.task

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.fristcode.task.eventBus.EventFragmentSelected
import com.fristcode.task.eventBus.EventProgressBarShowHide
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var myPb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPermission()
        myPb = findViewById(R.id.myPb)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)
    }

    private fun getPermission() {
        Dexter.withActivity(this)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@MainActivity, "You must accept this permission to use the app",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }).check()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onFragmentSelected(event: EventFragmentSelected) {
        navController.navigate(event.id)
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onProgressBarShowHide(eventBus: EventProgressBarShowHide) {
        if (eventBus.show) {
            myPb.visibility = View.VISIBLE
        } else {
            myPb.visibility = View.GONE
        }
    }
}