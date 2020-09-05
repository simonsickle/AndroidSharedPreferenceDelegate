package com.simonsickle.sharedpreferencedelegate

import android.content.SharedPreferences
import androidx.annotation.RestrictTo
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@RestrictTo(RestrictTo.Scope.LIBRARY)
class SharedPreferenceDelegate<T>(
    private val preferenceType: PreferenceType,
    private val sharedPreferences: SharedPreferences,
    private val defaultKey: String?,
    private val defaultValue: T?
) : ReadWriteProperty<Any?, T?> {
    @Suppress("UNCHECKED_CAST")
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        val key = defaultKey ?: property.name
        val editor = sharedPreferences.edit()
        when (preferenceType) {
            PreferenceType.STRING -> {
                editor.putString(key, value as String?)
            }
            PreferenceType.STRING_SET -> {
                editor.putStringSet(key, value as Set<String>?)
            }
            PreferenceType.INT -> {
                editor.putInt(key, value as Int)
            }
            PreferenceType.LONG -> {
                editor.putLong(key, value as Long)
            }
            PreferenceType.FLOAT -> {
                editor.putFloat(key, value as Float)
            }
            PreferenceType.BOOLEAN -> {
                editor.putBoolean(key, value as Boolean)
            }
        }
        editor.apply()
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        val key = defaultKey ?: property.name
        return when (preferenceType) {
            PreferenceType.STRING -> {
                sharedPreferences.getString(key, defaultValue as String?)
            }
            PreferenceType.STRING_SET -> {
                sharedPreferences.getStringSet(key, defaultValue as Set<String>?)
            }
            PreferenceType.INT -> {
                sharedPreferences.getInt(key, defaultValue as Int)
            }
            PreferenceType.LONG -> {
                sharedPreferences.getLong(key, defaultValue as Long)
            }
            PreferenceType.FLOAT -> {
                sharedPreferences.getFloat(key, defaultValue as Float)
            }
            PreferenceType.BOOLEAN -> {
                sharedPreferences.getBoolean(key, defaultValue as Boolean)
            }
        } as T?
    }
}