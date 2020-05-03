package com.scc.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import org.androidannotations.annotations.*

@EActivity(R.layout.activity_launcher)
class LauncherActivity : AppCompatActivity() {
    @ViewById(R.id.launcher_debug_info_container)
    private lateinit var debugInfoContainer: RelativeLayout
    @ViewById(R.id.launcher_app_version_text)
    private lateinit var appVersionText: TextView
    @ViewById(R.id.launcher_app_flavor_text)
    private lateinit var appFlavorText: TextView
    @ViewById(R.id.launcher_app_build_type_text)
    private lateinit var appBuildTypeText: TextView

    @ViewById(R.id.launcher_app_logo_image)
    private lateinit var appLogoImage: ImageView
    @ViewById(R.id.launcher_app_title_text)
    private lateinit var appTitleText: TextView

    @ViewById(R.id.launcher_loading_progress_bar)
    private lateinit var appLoadingProgressBar: ProgressBar
    @ViewById(R.id.launcher_error_message_text)
    private lateinit var appErrorMessageText: TextView

    @AfterViews
    fun setupViews() {
        if(BuildConfig.DEBUG) {
            debugInfoContainer.visibility = View.VISIBLE

            appVersionText.text = BuildConfig.VERSION_NAME
            appFlavorText.text = BuildConfig.FLAVOR
            appBuildTypeText.text = BuildConfig.BUILD_TYPE
        } else {
            debugInfoContainer.visibility = View.GONE
        }
    }
}
