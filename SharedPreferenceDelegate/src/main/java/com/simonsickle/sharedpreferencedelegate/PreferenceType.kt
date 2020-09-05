package com.simonsickle.sharedpreferencedelegate

import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY)
enum class PreferenceType {
    STRING,
    STRING_SET,
    INT,
    LONG,
    FLOAT,
    BOOLEAN
}