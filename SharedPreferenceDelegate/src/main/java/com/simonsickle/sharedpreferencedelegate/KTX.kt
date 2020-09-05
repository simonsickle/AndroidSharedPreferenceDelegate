package com.simonsickle.sharedpreferencedelegate

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty

sealed class SharedPreferenceDelegateConfig<T> {
    abstract fun getDelegate(): ReadWriteProperty<Any?, T?>

    protected abstract val sharedPreferences: SharedPreferences
    protected abstract val defaultKey: kotlin.String?
    protected abstract val defaultValue: T?

    class String(
        override val sharedPreferences: SharedPreferences,
        override val defaultKey: kotlin.String? = null,
        override val defaultValue: kotlin.String? = null
    ) : SharedPreferenceDelegateConfig<kotlin.String?>() {
        override fun getDelegate(): ReadWriteProperty<Any?, kotlin.String?> {
            return SharedPreferenceDelegate(
                PreferenceType.STRING,
                sharedPreferences,
                defaultKey,
                defaultValue
            )
        }
    }

    class StringSet(
        override val sharedPreferences: SharedPreferences,
        override val defaultKey: kotlin.String? = null,
        override val defaultValue: Set<kotlin.String>? = null
    ) : SharedPreferenceDelegateConfig<Set<kotlin.String>?>() {
        override fun getDelegate(): ReadWriteProperty<Any?, Set<kotlin.String>?> {
            return SharedPreferenceDelegate(
                PreferenceType.STRING_SET,
                sharedPreferences,
                defaultKey,
                defaultValue
            )
        }
    }

    class Integer(
        override val sharedPreferences: SharedPreferences,
        override val defaultKey: kotlin.String? = null,
        override val defaultValue: Int = 0
    ) : SharedPreferenceDelegateConfig<Int>() {
        override fun getDelegate(): ReadWriteProperty<Any?, Int?> {
            return SharedPreferenceDelegate(
                PreferenceType.INT,
                sharedPreferences,
                defaultKey,
                defaultValue
            )
        }
    }

    class Long(
        override val sharedPreferences: SharedPreferences,
        override val defaultKey: kotlin.String? = null,
        override val defaultValue: kotlin.Long = 0L
    ) : SharedPreferenceDelegateConfig<kotlin.Long>() {
        override fun getDelegate(): ReadWriteProperty<Any?, kotlin.Long?> {
            return SharedPreferenceDelegate(
                PreferenceType.LONG,
                sharedPreferences,
                defaultKey,
                defaultValue
            )
        }
    }

    class Float(
        override val sharedPreferences: SharedPreferences,
        override val defaultKey: kotlin.String? = null,
        override val defaultValue: kotlin.Float = 0F
    ) : SharedPreferenceDelegateConfig<kotlin.Float>() {
        override fun getDelegate(): ReadWriteProperty<Any?, kotlin.Float?> {
            return SharedPreferenceDelegate(
                PreferenceType.FLOAT,
                sharedPreferences,
                defaultKey,
                defaultValue
            )
        }
    }

    class Boolean(
        override val sharedPreferences: SharedPreferences,
        override val defaultKey: kotlin.String? = null,
        override val defaultValue: kotlin.Boolean = false
    ) : SharedPreferenceDelegateConfig<kotlin.Boolean>() {
        override fun getDelegate(): ReadWriteProperty<Any?, kotlin.Boolean?> {
            return SharedPreferenceDelegate(
                PreferenceType.BOOLEAN,
                sharedPreferences,
                defaultKey,
                defaultValue
            )
        }
    }
}

fun <T> sharedPreferenceDelegate(init: () -> SharedPreferenceDelegateConfig<T>): ReadWriteProperty<Any?, T?> {
    return init().getDelegate()
}
//
//fun test() {
//
//}
//
//fun <T> sharedPreferenceDelegate(): ReadWriteProperty<Any?, String?> {
//    return StringSharedPreferenceDelegate(sharedPreferences, key, defaultValue)
//}
//
//fun sharedPreferenceDelegate(
//    sharedPreferences: SharedPreferences,
//    key: String? = null,
//    defaultValue: Set<String>? = null
//): ReadWriteProperty<Any?, Set<String>?> {
//    return StringSetSharedPreferenceDelegate(sharedPreferences, key, defaultValue)
//}
//
//fun sharedPreferenceDelegate(
//    sharedPreferences: SharedPreferences,
//    key: String? = null,
//    defaultValue: Int
//): ReadWriteProperty<Any, Int> {
//    return IntSharedPreferenceDelegate(sharedPreferences, key, defaultValue)
//}
//
//fun sharedPreferenceDelegate(
//    sharedPreferences: SharedPreferences,
//    key: String? = null,
//    defaultValue: Long
//): ReadWriteProperty<Any, Long> {
//    return LongSharedPreferenceDelegate(sharedPreferences, key, defaultValue)
//}
//
//fun sharedPreferenceDelegate(
//    sharedPreferences: SharedPreferences,
//    key: String? = null,
//    defaultValue: Float
//): ReadWriteProperty<Any, Float> {
//    return FloatSharedPreferenceDelegate(sharedPreferences, key, defaultValue)
//}
//
//fun sharedPreferenceDelegate(
//    sharedPreferences: SharedPreferences,
//    key: String? = null,
//    defaultValue: Boolean
//): ReadWriteProperty<Any, Boolean> {
//    return BooleanSharedPreferenceDelegate(sharedPreferences, key, defaultValue)
//}