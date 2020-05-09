package com.example.bookshare.viewmodel

import AuthRepositoryImpl
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookshare.model.User
import com.example.bookshare.repository.AuthRepository

class ProfileViewModel : ViewModel() {

    private val mAuthRepository: AuthRepository<User> by lazy { AuthRepositoryImpl() }

    val userName = MutableLiveData<String>().apply {
        mAuthRepository.getCurrentUser()?.let {
            value = it.displayName
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel() as T
        }

    }
}
