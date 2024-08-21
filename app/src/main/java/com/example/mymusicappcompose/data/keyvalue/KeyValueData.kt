package com.example.mymusicappcompose.data.keyvalue

import android.content.Context
import android.content.SharedPreferences
import java.lang.ref.WeakReference

/**
 * A seguinte classe tem como responsabilidade:
 * + escrever dados chave/valor
 * + ler dados chave/valor
 * + remove dados chaves/valor
 * + apaga TODOS os dados chave/valor
 */
class KeyValueData(val context: WeakReference<Context>, val name: String) {

    private fun getSharedPreferences(): SharedPreferences? {
        return context.get()?.getSharedPreferences(
            name,
            Context.MODE_PRIVATE
        )
    }

    fun write(key: String, value: Any) {
        val sharedPref = getSharedPreferences() ?: return

        /*
            val editor: SharedPreferences.Editor = sharedPref.edit()
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
            }
            editor.apply()
        */

        with(sharedPref.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
            }
            apply()
        }
    }

    fun readString(key: String): String? {
        val sharedPref = getSharedPreferences() ?: return null
        return sharedPref.getString(key, null)
    }

    fun readLong(key: String): Long? {
        val sharedPref = getSharedPreferences() ?: return null
        return sharedPref.getLong(key, 0L)
    }

    fun readBoolean(key: String): Boolean? {
        val sharedPref = getSharedPreferences() ?: return null
        return sharedPref.getBoolean(key, false)
    }

    fun readInt(key: String): Int? {
        val sharedPref = getSharedPreferences() ?: return null
        return sharedPref.getInt(key, 0)
    }

    fun readFloat(key: String): Float?{
        val sharedPref = getSharedPreferences() ?: return null
        return sharedPref.getFloat(key, 0F)
    }

    fun clear(): Boolean {
        val sharedPref = getSharedPreferences() ?: return false

        return with(sharedPref.edit()) {
            clear()
            commit()
        }
    }

    fun remove(key: String): Boolean {
        val sharedPref = getSharedPreferences() ?: return false

        return with(sharedPref.edit()) {
            remove(key)
            commit()
        }
    }
}
