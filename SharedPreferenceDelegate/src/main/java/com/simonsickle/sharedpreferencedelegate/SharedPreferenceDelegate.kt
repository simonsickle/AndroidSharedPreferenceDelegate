package com.simonsickle.sharedpreferencedelegate

import android.content.SharedPreferences
import androidx.annotation.RestrictTo
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@RestrictTo(RestrictTo.Scope.LIBRARY)
class SharedPreferenceDelegate<T>(
    private val preferenceType: PreferenceType,
    private val sharedPreferences: SharedPreferences,
    private val keyName: String?,
    private val defaultValue: T?
) : ReadWriteProperty<Any?, T> {
    @Suppress("UNCHECKED_CAST")
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val key = keyName ?: property.name
        sharedPreferences.edit {
            when (preferenceType) {
                PreferenceType.STRING -> {
                    putString(key, value as? String)
                }
                PreferenceType.STRING_SET -> {
                    putStringSet(key, value as? Set<String>)
                }
                PreferenceType.INT -> {
                    putInt(key, value as? Int ?: -1)
                }
                PreferenceType.LONG -> {
                    putLong(key, value as? Long ?: -1L)
                }
                PreferenceType.FLOAT -> {
                    putFloat(key, value as? Float ?: 0F)
                }
                PreferenceType.BOOLEAN -> {
                    putBoolean(key, value as? Boolean ?: false)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val key = keyName ?: property.name
        return when (preferenceType) {
            PreferenceType.STRING -> {
                sharedPreferences.getString(key, defaultValue as? String ?: "")
            }
            PreferenceType.STRING_SET -> {
                sharedPreferences.getStringSet(key, defaultValue as? Set<String> ?: emptySet())
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
        } as T
    }
}

/**
 * delegate shared preference access to a var, without allowing null
 *
 * @param keyName the key's name in shared preferences. Defaults to variable name.
 * @param defaultValue the default value of the entry. Defaults: empty for string/set, -1 for int/long, 0 for float
 */
inline fun <reified T : Any> SharedPreferences.delegate(
    keyName: String? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any?, T> {
    return SharedPreferenceDelegate(
        preferenceType = getTypeOfPreference(T::class),
        sharedPreferences = this,
        keyName = keyName,
        defaultValue = defaultValue
    )
}

// TODO: is there a better way to work with the primitive generics?
fun getTypeOfPreference(kClass: KClass<*>): PreferenceType {
    return when {
        kClass.java.isAssignableFrom(String::class.java) -> PreferenceType.STRING
        kClass.java.isAssignableFrom(Set::class.java) -> PreferenceType.STRING_SET
        kClass.simpleName == "Int" -> PreferenceType.INT
        kClass.simpleName == "Long" -> PreferenceType.LONG
        kClass.simpleName == "Float" -> PreferenceType.FLOAT
        kClass.simpleName == "Boolean" -> PreferenceType.BOOLEAN
        else -> {
            throw IllegalArgumentException("Type ${kClass.simpleName} is not supported by this delegate")
        }
    }
}