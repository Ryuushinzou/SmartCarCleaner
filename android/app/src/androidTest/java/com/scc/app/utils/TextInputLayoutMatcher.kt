package com.scc.app.utils

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

fun hasTextInputLayoutErrorText(expectedErrorValue: String) = object:
    org.hamcrest.TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        //  do nothing
    }

    override fun matchesSafely(item: View?): Boolean {
        if(item !is TextInputLayout) return false

        val errorText = item.error ?: return false
        return expectedErrorValue == errorText
    }
}