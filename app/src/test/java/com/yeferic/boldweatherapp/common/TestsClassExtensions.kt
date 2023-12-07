package com.yeferic.boldweatherapp.common

fun <V, T : Any> setPrivatePropertyValue(objectClass: T, fieldName: String, value: V) {
    objectClass::class.java.getDeclaredField(fieldName).apply {
        isAccessible = true
        set(objectClass, value)
    }
}

fun <T> getPrivatePropertyValue(objectClass: Any, fieldName: String): T {
    return objectClass::class.java.getDeclaredField(fieldName).apply {
        isAccessible = true
    }.get(objectClass) as T
}
