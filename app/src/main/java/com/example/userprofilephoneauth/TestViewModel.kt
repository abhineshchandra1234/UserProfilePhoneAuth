package com.example.userprofilephoneauth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel: ViewModel() {

    val userExist: MutableLiveData<String> by lazy {	//declare LiveData variables
        MutableLiveData<String>()
    }
}