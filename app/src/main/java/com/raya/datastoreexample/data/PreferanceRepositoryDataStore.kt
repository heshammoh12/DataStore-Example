package com.raya.datastoreexample.data

import android.content.Context
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferanceRepositoryDataStore(val context: Context) {
    val PREFERENCE_NAME = "user_data.proto"
    companion object {
        val USER_NAME_KEY = preferencesKey<String>("user_name_key")
        val USER_AGE_KEY = preferencesKey<Int>("user_age_key")
    }

    private val dataStore = context.createDataStore(
        name = PREFERENCE_NAME, migrations = listOf(
            SharedPreferencesMigration(context, PREFERENCE_NAME)
        )
    )

    suspend fun setUserName(value: String) {
        dataStore.edit { preferance ->
            preferance[USER_NAME_KEY] = value
        }
    }

    suspend fun setAge(value: Int) {
        dataStore.edit {
            it[USER_AGE_KEY] = value
        }
    }

    fun getUserName(): Flow<String> {
        return dataStore.data.map {
            it[USER_NAME_KEY] ?: ""
        }
    }

    fun getAge(): Flow<Int> {
        return dataStore.data.map {
            it[USER_AGE_KEY] ?: -1
        }
    }

}