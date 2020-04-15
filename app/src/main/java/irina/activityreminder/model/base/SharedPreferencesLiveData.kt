package irina.activityreminder.model.base

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


abstract class SharedPreferencesLiveData<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defValue: T
) : MutableLiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == this.key) {
                value = getValueFromPreferences(this.sharedPreferences, key, defValue)
            }
        }

    private val observer: Observer<in T> = Observer {
        saveValueToPreferences(sharedPreferences, key, value!!)
    }

    init {
        value = this.getValueFromPreferences(sharedPreferences, key, defValue)
        this.observeForever(observer)
    }

    abstract fun saveValueToPreferences(sharedPreferences: SharedPreferences, key: String, value: T)

    abstract fun getValueFromPreferences(
        sharedPreferences: SharedPreferences,
        key: String,
        defValue: T
    ): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(sharedPreferences, key, defValue)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        observeForever(observer)
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
        removeObserver(observer)
    }
}

class StringSharedPreferencesLiveData(
    sharedPrefs: SharedPreferences, key: String,
    defValue: String
) : SharedPreferencesLiveData<String>(
    sharedPrefs, key, defValue
) {
    override fun getValueFromPreferences(
        sharedPreferences: SharedPreferences,
        key: String,
        defValue: String
    ): String {
        return sharedPreferences.getString(key, defValue)!!
    }

    override fun saveValueToPreferences(
        sharedPreferences: SharedPreferences,
        key: String,
        value: String
    ) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}

class IntSharedPreferencesLiveData(
    sharedPrefs: SharedPreferences, key: String,
    defValue: Int
) : SharedPreferencesLiveData<Int>(
    sharedPrefs, key, defValue
) {
    override fun getValueFromPreferences(
        sharedPreferences: SharedPreferences,
        key: String,
        defValue: Int
    ): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    override fun saveValueToPreferences(
        sharedPreferences: SharedPreferences,
        key: String,
        value: Int
    ) {
        sharedPreferences.edit().putInt(key, value).apply()
    }
}