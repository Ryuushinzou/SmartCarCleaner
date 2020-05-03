package com.scc.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContextTest {
    @Test
    fun shouldSetPackageNameBasedOnBuildType() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val appPackageName = "com.scc.app" + if(BuildConfig.DEBUG) {
            ".debug"
        } else {
            ""
        }

        Assert.assertEquals("Expected: $appPackageName Got: ${appContext.packageName}", appPackageName, appContext.packageName)
    }
}