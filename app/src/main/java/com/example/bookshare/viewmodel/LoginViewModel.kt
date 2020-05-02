package com.example.bookshare.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.bookshare.model.User

class LoginViewModel : ViewModel() {


    sealed class NavigationCommand{
        object ToSignUpFragment : NavigationCommand()
        object ShowWrongEmailMessage: NavigationCommand()
        object ShowWrongPasswordMessage: NavigationCommand()

    }

    private val mNavigationCommandSender = MutableLiveData<NavigationCommand>()

    private val mUserNameObserver = Observer<String>{
        Log.d("SET",it)
        user.email= it
    }

    private val mPasswordObserver = Observer<String> {
        Log.d("SET",it)
        user.password = it
    }

    private val user = User()

    val navigationCommandSender: LiveData<NavigationCommand>
    get() = mNavigationCommandSender

    val userName = MutableLiveData<String>()
    get() = field

    val password = MutableLiveData<String>()
    get() = field

    init {
        userName.observeForever(mUserNameObserver)
        password.observeForever(mPasswordObserver)
    }

    fun jumpToSignUp(){
        println("Test")
        mNavigationCommandSender.postValue(NavigationCommand.ToSignUpFragment)
    }

    fun login(){

        if(user.email == "")
            mNavigationCommandSender.postValue(NavigationCommand.ShowWrongEmailMessage)
        else if(user.password == "")
            mNavigationCommandSender.postValue(NavigationCommand.ShowWrongPasswordMessage)
            //TODO login to firebase
    }

    @Suppress("UNCHECKED_CAST")
    class Factory: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel() as T
        }

    }

    override fun onCleared() {
        super.onCleared()
        userName.removeObserver(mUserNameObserver)
        password.removeObserver(mPasswordObserver)
    }
}
