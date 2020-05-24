package com.scc.app

import android.util.Log
import android.view.autofill.AutofillManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.scc.app.utils.TestAccountGenerator
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_VALID_PHONE = "0700000000"
private const val TEST_VALID_PASSWORD = "Pass123!"

private const val TEST_INVALID_USERNAME = "@test"
private const val TEST_INVALID_PHONE = "0000000000"
private const val TEST_EMPTY_PASSWORD = ""
private const val TEST_INVALID_PASSWORD = "pwd"

@RunWith(AndroidJUnit4::class)
@LargeTest
class RegisterActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<RegisterActivity> =
        ActivityTestRule(RegisterActivity::class.java, false, true)

    private val testEmailGenerator = TestAccountGenerator()

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
    fun verifyRegisterNewUserWithValidDataIsSuccessful() {
        val testUsername = testEmailGenerator.generateUsername()
        val testEmail = testEmailGenerator.generateTestEmail()
        Log.d(RegisterActivityTest::class.java.simpleName, "Running test with username: $testUsername || email: $testEmail")

        onView(withId(R.id.register_username_input))
            .perform(replaceText(testUsername))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.register_email_input))
            .perform(replaceText(testEmail))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.register_phone_input))
            .perform(replaceText(TEST_VALID_PHONE))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.register_password_input))
            .perform(replaceText(TEST_VALID_PASSWORD))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.register_confirm_password_input))
            .perform(replaceText(TEST_VALID_PASSWORD))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.register_submit_button))
            .perform(click())

        Thread.sleep(10 * 1000)

        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun verifyRegisterNewUserWithoutUsernameDisplaysError() {
        assert(false) { "Test not implemented" }
    }

    @Test
    fun verifyRegisterNewUserWithoutEmailDisplaysError() {
        assert(false) { "Test not implemented" }
    }

    @Test
    fun verifyRegisterNewUserWithoutPasswordDisplaysError() {
        assert(false) { "Test not implemented" }
    }

    @Test
    fun verifyRegisterNewUserWithoutConfirmingPasswordDisplaysError() {
        assert(false) { "Test not implemented" }
    }

    @Test
    fun verifyRegisterNewUserWithInvalidUsernameDisplaysError() {
        assert(false) { "Test not implemented" }
    }

    @Test
    fun verifyRegisterNewUserWithInvalidEmailDisplaysError() {
        assert(false) { "Test not implemented" }
    }

    @Test
    fun verifyRegisterNewUserWithInvalidPasswordDisplaysError() {
        assert(false) { "Test not implemented" }
    }
}