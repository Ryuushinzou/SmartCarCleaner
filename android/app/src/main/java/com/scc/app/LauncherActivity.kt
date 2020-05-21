package com.scc.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

class LauncherActivity : AppCompatActivity() {
    private lateinit var debugInfoContainer: RelativeLayout
    private lateinit var appVersionText: TextView
    private lateinit var appFlavorText: TextView
    private lateinit var appBuildTypeText: TextView
    private lateinit var appLogoImage: ImageView
    private lateinit var appTitleText: TextView
    private lateinit var appLoadingProgressBar: ProgressBar
    private lateinit var appErrorMessageText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        bindViews()
        setupViews()

        Handler().postDelayed({
            runOnUiThread {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }, 2000)
    }

    private fun bindViews() {
        debugInfoContainer = findViewById(R.id.launcher_debug_info_container)
        appVersionText = findViewById(R.id.launcher_app_version_text)
        appFlavorText = findViewById(R.id.launcher_app_flavor_text)
        appBuildTypeText = findViewById(R.id.launcher_app_build_type_text)
        appLogoImage = findViewById(R.id.launcher_app_logo_image)
        appTitleText = findViewById(R.id.launcher_app_title_text)
        appLoadingProgressBar = findViewById(R.id.launcher_loading_progress_bar)
        appErrorMessageText = findViewById(R.id.launcher_error_message_text)
    }

    private fun setupViews() {
        if (BuildConfig.DEBUG) {
            debugInfoContainer.visibility = View.VISIBLE

            appVersionText.text = BuildConfig.VERSION_NAME
            appFlavorText.text = BuildConfig.FLAVOR
            appBuildTypeText.text = BuildConfig.BUILD_TYPE
        } else {
            debugInfoContainer.visibility = View.GONE
        }
    }
}
