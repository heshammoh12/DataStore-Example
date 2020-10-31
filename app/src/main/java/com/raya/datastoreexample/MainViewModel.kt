package com.raya.datastoreexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raya.datastoreexample.data.PreferanceRepositoryDataStore
import com.raya.datastoreexample.data.PreferanceRepositoryProtoStore
import com.raya.datastoreexample.data.UserModel
import kotlinx.coroutines.launch

class MainViewModel(
    val preferanceManager: PreferanceRepositoryDataStore,
    val preferanceRepositoryProtoStore: PreferanceRepositoryProtoStore
) : ViewModel() {

    //datastore
    val userNameLiveData = preferanceManager.getUserName().asLiveData()
    val userAgeLiveData = preferanceManager.getAge().asLiveData()

    //proto datastore
    var userLiveData = preferanceRepositoryProtoStore.userDataFlow.asLiveData()

    fun getUserName(): String? {
        return preferanceManager.getUserName().asLiveData().value
    }

    fun getUserAge(): String? {
        if (preferanceManager.getAge().asLiveData().value == -1)
            return ""
        else
            return preferanceManager.getAge().asLiveData().value.toString()
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            preferanceManager.setUserName(name)
        }
    }

    fun saveUserAge(age: String) {
        viewModelScope.launch {
            preferanceManager.setAge(age.toInt())
        }
    }

    fun saveUser(userModel: UserModel) {
        viewModelScope.launch{
            preferanceRepositoryProtoStore.saveUser(UserModel(userModel.userName,userModel.userAge))
        }
    }
}