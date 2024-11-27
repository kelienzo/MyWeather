package com.kelly.core.common

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private fun SharedPreferences.getStringFlow(
        key: String
    ): Flow<String> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, updatedKey ->
            if (updatedKey == key) {
                trySend(getString(key, "").orEmpty())
            }
        }

        registerOnSharedPreferenceChangeListener(listener)
        trySend(getString(key, "").orEmpty())

        awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
    }

    private fun SharedPreferences.getBooleanFlow(
        key: String,
        defaultValue: Boolean = false
    ): Flow<Boolean> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, updatedKey ->
            if (updatedKey == key) {
                trySend(getBoolean(key, defaultValue))
            }
        }

        registerOnSharedPreferenceChangeListener(listener)
        trySend(getBoolean(key, defaultValue))

        awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
    }

    val isLocationGranted get() = getSavedBooleanAsFlow(Constants.IS_LOCATION_GRANTED)

    fun saveStringData(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun saveBooleanData(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun getSavedStringData(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue).orEmpty()
    }

    fun getSavedStringDataAsFlow(key: String) = sharedPreferences.getStringFlow(key)

    fun getSavedBooleanAsFlow(key: String) = sharedPreferences.getBooleanFlow(key)

    fun getSavedBooleanData(key: String, defaultValue: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)
}