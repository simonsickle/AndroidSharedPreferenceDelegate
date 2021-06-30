Android SharedPreferences Delegate
==================================

To use this library, add maven central to your sources. Then add the following

```
implementation("com.simonsickle:sharedpreference-delegate:1.0.0")
```

Usage
-----

When you want to use shared preferences, you can now call it in
a simplified manor. EncryptedSharedPreferences also works with this extension.

```kotlin
val prefs = context.getSharedPreferences("nameOfPrefs", Context.MODE_PRIVATE)

...

var someField: String by prefs.delegate()

// You can also set a default value and key name for the preferences
var someOtherField: String by prefs.delegate(keyName = "SomeCoolPreference", defaultValue = "Hello")

...
// and accessing / setting the value is a breeze!
someField = "Hello, Dave!"
fun doSomething(): String {
    return "$someField I am afraid I can't allow you to do that..."
}
```

Supported Types
---------------
- String (defaultValue is an empty string `""`)
- String Set (defaultValue is an empty StringSet `emptySet<String>()`)
- Int (defaultValue is -1)
- Long (defaultValue is -1L)
- Float (defaultValue is 0F)
- Boolean (defaultValue is false)
