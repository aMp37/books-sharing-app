package com.example.bookshare.viewmodel

import AuthRepositoryImpl
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookshare.model.User
import com.example.bookshare.repository.AuthRepository
import com.example.bookshare.util.SingleLiveEvent

class ProfileViewModel : ViewModel() {

    private val mAuthRepository: AuthRepository<User> by lazy { AuthRepositoryImpl }

    val user = MutableLiveData<User>().apply {
        value = mAuthRepository.getCurrentUser()
    }

    val navigationCommandSender = SingleLiveEvent<NavigationCommand>()

    @Suppress("UNCHECKED_CAST")
    class Factory: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel() as T
        }
    }

    fun jumpToEditProfile(){
        navigationCommandSender.value = NavigationCommand.ToProfileEditFragment
    }

    fun jumpToLoginScreen(){
        navigationCommandSender.value = NavigationCommand.ToLoginScreen
    }

    fun logoutUser(){
        mAuthRepository.signOutUser()
        jumpToLoginScreen()
    }

    sealed class NavigationCommand{
        object ToProfileEditFragment: NavigationCommand()
        object ToLoginScreen: NavigationCommand()
    }
}
