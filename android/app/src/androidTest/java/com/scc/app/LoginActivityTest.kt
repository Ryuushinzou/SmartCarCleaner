package com.scc.app

import android.view.autofill.AutofillManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_VALID_USERNAME = "mihai"
private const val TEST_VALID_PASSWORD = "mihai"

private const val TEST_INVALID_USERNAME = "invalid"
private const val TEST_INVALID_PASSWORD = "pass"

private const val INVALID_CREDENTIALS_MESSAGE = "Invalid username or password"

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> =
        ActivityTestRule(LoginActivity::class.java, false, true)

    @Before
    fun setup() {
        val testContext = InstrumentationRegistry.getInstrumentation().targetContext
        val autofillManager = testContext.getSystemService(AutofillManager::class.java)
        autofillManager?.disableAutofillServices()

        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun verifyLoginToExistingAccountIsSuccessful() {
        onView(withId(R.id.login_username_input))
            .perform(replaceText(TEST_VALID_USERNAME)).perform(closeSoftKeyboard())

        onView(withId(R.id.login_password_input))
            .perform(replaceText(TEST_VALID_PASSWORD)).perform(closeSoftKeyboard())

        onView(withId(R.id.login_submit_button))
            .perform(click())

        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun verifyLoginWithInvalidCredentialsDisplaysErrors() {
        onView(withId(R.id.login_username_input))
            .perform(replaceText(TEST_INVALID_USERNAME))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.login_password_input))
            .perform(replaceText(TEST_INVALID_PASSWORD))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.login_submit_button))
            .perform(click())

        onView(withId(R.id.login_username_container))
            .check(matches(hasTextInputLayoutErrorText(INVALID_CREDENTIALS_MESSAGE)))
    }
}