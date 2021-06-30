package com.simonsickle.sharedpreferencedelegate

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
        var stringPreference: String by preferences.delegate()

        assertEquals("", stringPreference)
        stringPreference = "I don't know about this rick!"
        assertEquals("I don't know about this rick!", stringPreference)
        assertEquals(
            "I don't know about this rick!",
            preferences.getString("stringPreference", null)
        )
    }

    @Test
    fun whenDelegatingStringSet_ReadWriteWorks() {
        var stringSetPreference: Set<String> by preferences.delegate()

        assertEquals(emptySet<String>(), stringSetPreference)
        stringSetPreference = setOf("I don't know about this rick!", "I'm Pickle Rick!")
        assertTrue(stringSetPreference.contains("I don't know about this rick!"))
        assertTrue(stringSetPreference.contains("I'm Pickle Rick!"))

        val directAccess = preferences.getStringSet("stringSetPreference", emptySet())
        assertTrue(directAccess!!.contains("I don't know about this rick!"))
        assertTrue(directAccess.contains("I'm Pickle Rick!"))
    }


    @Test
    fun whenDelegatingStringsWithKey_ReadWriteWorks() {
        var stringPreference: String by preferences.delegate(keyName = "someKey")

        assertEquals("", stringPreference)
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
        var intPreference: Int by preferences.delegate()

        assertEquals(-1, intPreference)
        intPreference = 42
        assertEquals(42, intPreference)
        assertEquals(
            42,
            preferences.getInt("intPreference", -1)
        )
    }

    @Test
    fun whenDelegatingLong_ReadWriteWorks() {
        var longPreference: Long by preferences.delegate()

        assertEquals(-1L, longPreference)
        longPreference = 42L
        assertEquals(42L, longPreference)
        assertEquals(
            42L,
            preferences.getLong("longPreference", -1L)
        )
    }

    @Test
    fun whenDelegatingFloat_ReadWriteWorks() {
        var floatPreference: Float by preferences.delegate()

        assertEquals(0F, floatPreference)
        floatPreference = 42f
        assertEquals(42f, floatPreference)
        assertEquals(
            42f,
            preferences.getFloat("floatPreference", 0F)
        )
    }

    @Test
    fun whenDelegatingBool_ReadWriteWorks() {
        var boolPreference: Boolean by preferences.delegate()

        assertEquals(false, boolPreference)
        boolPreference = true
        assertEquals(true, boolPreference)
        assertEquals(
            true,
            preferences.getBoolean("boolPreference", false)
        )
    }
}