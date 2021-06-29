package com.simonsickle.sharedpreferencedelegate

import android.content.SharedPreferences
import androidx.annotation.RestrictTo
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@RestrictTo(RestrictTo.Scope.LIBRARY)
class SharedPreferenceDelegate<T>(
    private val preferenceType: PreferenceType,
    private val sharedPreferences: SharedPreferences,
    private val keyName: String?,
    private val defaultValue: T?
) : ReadWriteProperty<Any?, T?> {
    @Suppress("UNCHECKED_CAST")
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        val key = keyName ?: property.name
        val editor = sharedPreferences.edit()
        when (preferenceType) {
            PreferenceType.STRING -> {
                editor.putString(key, value as String?)
            }
            PreferenceType.STRING_SET -> {
                editor.putStringSet(key, value as Set<String>?)
            }
            PreferenceType.INT -> {
                editor.putInt(key, value as? Int ?: -1)
            }
            PreferenceType.LONG -> {
                editor.putLong(key, value as? Long ?: -1L)
            }
            PreferenceType.FLOAT -> {
                editor.putFloat(key, value as? Float ?: 0F)
            }
            PreferenceType.BOOLEAN -> {
                editor.putBoolean(key, value as? Boolean ?: false)
            }
        }
        editor.apply()
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        val key = keyName ?: property.name
        return when (preferenceType) {
            PreferenceType.STRING -> {
                sharedPreferences.getString(key, defaultValue as String?)
            }
            PreferenceType.STRING_SET -> {
                sharedPreferences.getStringSet(key, defaultValue as Set<String>?)
            }
            PreferenceType.INT -> {
                sharedPreferences.getInt(key, defaultValue as? Int ?: -1)
            }
            PreferenceType.LONG -> {
                sharedPreferences.getLong(key, defaultValue as? Long ?: -1L)
            }
            PreferenceType.FLOAT -> {
                sharedPreferences.getFloat(key, defaultValue as? Float ?: 0F)
            }
            PreferenceType.BOOLEAN -> {
                sharedPreferences.getBoolean(key, defaultValue as? Boolean ?: false)
            }
        } as T?
    }
}

/**
 * delegate shared preference access to a var
 *
 * @param keyName the key's name in shared preferences. Defaults to variable name.
 * @param defaultValue the default value of the entry. Defaults: nulls for string/set, -1 for int/long, 0 for float
 */
inline fun <reified T : Any?> SharedPreferences.delegate(
    keyName: String? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any?, T?> {
    val clazz: Class<T> = T::class.java

    // TODO: is there a better way to work with the primitive generics?
    val typeOfPreference = when {
        clazz.isAssignableFrom(String::class.java) -> PreferenceType.STRING
        clazz.isAssignableFrom(Set::class.java) -> PreferenceType.STRING_SET
        T::class.simpleName == "Int" -> PreferenceType.INT
        T::class.simpleName == "Long" -> PreferenceType.LONG
        T::class.simpleName == "Float" -> PreferenceType.FLOAT
        T::class.simpleName == "Boolean" -> PreferenceType.BOOLEAN
        else -> {
            throw IllegalArgumentException("Type ${T::class.simpleName} is not supported by this delegate")
        }
    }

    return SharedPreferenceDelegate(
        preferenceType = typeOfPreference,
        sharedPreferences = this,
        keyName = keyName,
        defaultValue = defaultValue
    )
}