package com.raya.datastoreexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raya.datastoreexample.data.PreferanceRepositoryDataStore
import com.raya.datastoreexample.data.PreferanceRepositoryProtoStore

class MainViewModelFactory(val preferanceManager: PreferanceRepositoryDataStore, val preferanceRepositoryProtoStore: PreferanceRepositoryProtoStore) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(preferanceManager, preferanceRepositoryProtoStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}