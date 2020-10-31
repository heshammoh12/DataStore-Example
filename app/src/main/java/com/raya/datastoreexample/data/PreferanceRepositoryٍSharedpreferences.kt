package com.raya.datastoreexample.data

import android.content.Context
import androidx.core.content.edit

class `PreferanceRepositoryٍSharedpreferences`(val context: Context) {

    val PREFERENCE_NAME = "user_data.proto"

    private val sharedPreferance = context.getSharedPreferences(
        PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    fun setString(key: String, value: String) {
        sharedPreferance.edit {
            putString(key, value)
        }
    }

    fun setInt(key: String, value: Int) {
        sharedPreferance.edit {
            putInt(key, value)
        }
    }

    fun getString(key: String): String? {
        return sharedPreferance.getString(key, "")
    }

    fun getInt(key: String): Int? {
        return sharedPreferance.getInt(key, -1)
    }

}