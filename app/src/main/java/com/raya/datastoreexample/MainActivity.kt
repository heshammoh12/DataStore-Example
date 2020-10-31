package com.raya.datastoreexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.raya.datastoreexample.data.PreferanceRepositoryDataStore
import com.raya.datastoreexample.data.PreferanceRepositoryProtoStore
import com.raya.datastoreexample.data.UserModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(
                PreferanceRepositoryDataStore(
                    this
                ),
                PreferanceRepositoryProtoStore(this)
            )
        )
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUI()

    }

    private fun updateUI() {
        //DataStore
        /*   viewModel.userNameLiveData.observe(this, Observer {
               text_view_name.text = it
           })
           viewModel.userAgeLiveData.observe(this, Observer {
               text_view_Age.text = it.toString()
           })*/
        //Proto DataStore
        viewModel.userLiveData.observe(this, Observer {
            text_view_name.text = it.userName
            text_view_Age.text = it.age
        })
    }

    fun onSave(view: View) {
        //DataStore
/*        viewModel.saveUserName(editTextTextPersonName.text.toString())
        viewModel.saveUserAge(editTextTextPersonAge.text.toString())*/

        //ProtoData Store
        viewModel.saveUser(
            UserModel(
                editTextTextPersonName.text.toString(),
                editTextTextPersonAge.text.toString()
            )
        )
    }
}