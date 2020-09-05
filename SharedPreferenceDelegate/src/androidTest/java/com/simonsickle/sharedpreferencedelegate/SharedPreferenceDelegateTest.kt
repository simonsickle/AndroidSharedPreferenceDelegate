package com.simonsickle.sharedpreferencedelegate

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferenceDelegateTest {
    private val context = InstrumentationRegistry.getInstrumentation().context

    private val preferences =
        context.getSharedPreferences("delegate.tests", Context.MODE_PRIVATE)

    @Before
    fun setup() {
        preferences.edit().clear().commit()
        assertEquals(0, preferences.all.size)
    }

    @After
    fun cleanup() {
        preferences.edit().clear().commit()
        assertEquals(0, preferences.all.size)
    }

    @Test
    fun whenDelegatingStrings_ReadWriteWorks() {
        var stringPreference: String? by sharedPreferenceDelegate {
            SharedPreferenceDelegateConfig.String(preferences)
        }

        assertEquals(null, stringPreference)
        stringPreference = "I don't know about this rick!"
        assertEquals("I don't know about this rick!", stringPreference)
        assertEquals(
            "I don't know about this rick!",
            preferences.getString("stringPreference", null)
        )
    }

    @Test
    fun whenDelegatingStringsWithKey_ReadWriteWorks() {
        var stringPreference: String? by sharedPreferenceDelegate {
            SharedPreferenceDelegateConfig.String(preferences, defaultKey = "someKey")
        }

        assertEquals(null, stringPreference)
        stringPreference = "I don't know about this rick!"
        assertEquals("I don't know about this rick!", stringPreference)
        assertEquals(
            "I don't know about this rick!",
            preferences.getString("someKey", null)
        )
        assertEquals(
            null,
            preferences.getString("stringPreference", null)
        )
    }

    @Test
    fun whenDelegatingInt_ReadWriteWorks() {
        var intPreference: Int? by sharedPreferenceDelegate {
            SharedPreferenceDelegateConfig.Integer(preferences)
        }

        assertEquals(0, intPreference)
        intPreference = 42
        assertEquals(42, intPreference)
        assertEquals(
            42,
            preferences.getInt("intPreference", -1)
        )
    }
}