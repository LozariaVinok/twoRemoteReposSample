package irina.activityreminder.model

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.annotation.Nullable
import java.util.*


/**
 * Mock implementation of shared preference, which just saves data in memory using map.
 */
class MockSharedPreference : SharedPreferences {
    private val preferenceMap: HashMap<String, Any?> = HashMap()
    private val preferenceEditor: MockSharedPreferenceEditor
    override fun getAll(): Map<String, *> {
        return preferenceMap
    }

    @Nullable
    override fun getString(s: String, @Nullable s1: String?): String? {
        return if (preferenceMap[s] == null) s1 else preferenceMap[s] as String?
    }

    @Nullable
    override fun getStringSet(
        s: String,
        @Nullable set: Set<String>?
    ): Set<String>? {
        return preferenceMap[s] as Set<String>?
    }

    override fun getInt(s: String, i: Int): Int {
        return if (preferenceMap[s] == null) i else preferenceMap[s] as Int
    }

    override fun getLong(s: String, l: Long): Long {
        return if (preferenceMap[s] == null) l else preferenceMap[s] as Long
    }

    override fun getFloat(s: String, v: Float): Float {
        return preferenceMap[s] as Float
    }

    override fun getBoolean(s: String, b: Boolean): Boolean {
        return preferenceMap[s] as Boolean
    }

    override fun contains(s: String): Boolean {
        return preferenceMap.containsKey(s)
    }

    override fun edit(): Editor {
        return preferenceEditor
    }

    override fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: OnSharedPreferenceChangeListener) {}
    override fun unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: OnSharedPreferenceChangeListener) {}
    class MockSharedPreferenceEditor(private val preferenceMap: HashMap<String, Any?>) :
        Editor {
        override fun putString(s: String, @Nullable s1: String?): Editor {
            preferenceMap[s] = s1
            return this
        }

        override fun putStringSet(
            s: String,
            @Nullable set: Set<String>?
        ): Editor {
            preferenceMap[s] = set
            return this
        }

        override fun putInt(s: String, i: Int): Editor {
            preferenceMap[s] = i
            return this
        }

        override fun putLong(s: String, l: Long): Editor {
            preferenceMap[s] = l
            return this
        }

        override fun putFloat(s: String, v: Float): Editor {
            preferenceMap[s] = v
            return this
        }

        override fun putBoolean(s: String, b: Boolean): Editor {
            preferenceMap[s] = b
            return this
        }

        override fun remove(s: String): Editor {
            preferenceMap.remove(s)
            return this
        }

        override fun clear(): Editor {
            preferenceMap.clear()
            return this
        }

        override fun commit(): Boolean {
            return true
        }

        override fun apply() {
            // Nothing to do, everything is saved in memory.
        }

    }

    init {
        preferenceEditor = MockSharedPreferenceEditor(preferenceMap)
    }
}