package com.raya.datastoreexample.data

import android.content.Context
import android.util.Log
import androidx.datastore.CorruptionException
import androidx.datastore.DataStore
import androidx.datastore.Serializer
import androidx.datastore.createDataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.raya.datastoreexample.UserDataPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import java.io.InputStream

import java.io.OutputStream

class PreferanceRepositoryProtoStore(context: Context) {
    private val TAG: String = "UserPreferencesRepo"
    private val dataStore: DataStore<UserDataPref> =
        context.createDataStore(
            fileName = "user_prefs.pb",
            serializer = UserDataPrefSerializer
        )

    suspend fun saveUser(userData: UserModel) {
        //DataStore offers a suspending DataStore.updateData() function, where we get as parameter the current state of UserPreferences. To update it
        dataStore.updateData { preferences ->
            preferences.toBuilder().setUserName(userData.userName).setAge(userData.userAge).build()
        }
    }


    val userDataFlow: Flow<UserDataPref> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(UserDataPref.getDefaultInstance())
            } else {
                throw exception
            }
        }


    object UserDataPrefSerializer : Serializer<UserDataPref> {
        override fun readFrom(input: InputStream): UserDataPref {
            try {
                return UserDataPref.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override fun writeTo(
            t: UserDataPref,
            output: OutputStream
        ) = t.writeTo(output)
    }
}