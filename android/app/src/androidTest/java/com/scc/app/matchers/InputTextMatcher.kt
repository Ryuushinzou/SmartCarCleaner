package com.scc.app.matchers

import android.view.View
import android.widget.EditText
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun hasTextInput(expectedText: String) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        //  do nothing
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is EditText) return false

        val inputText = item.text ?: return false
        return expectedText.contentEquals(inputText.toString())
    }
}