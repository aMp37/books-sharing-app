package com.example.bookshare.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.bookshare.model.User
import com.example.bookshare.util.SingleLiveEvent

class LoginViewModel : ViewModel() {

    private val mNavigationCommandSender = SingleLiveEvent<NavigationCommand>()
    private val mUser = User()

    private val mUserNameObserver = Observer<String>{
        Log.d("SET",it)
        mUser.email= it
    }

    private val mPasswordObserver = Observer<String> {
        Log.d("SET",it)
        mUser.password = it
    }

    val navigationCommandSender: LiveData<NavigationCommand>
    get() = mNavigationCommandSender

    //Error messages visibility
    val emailErrorVisibility = MutableLiveData<Int>().apply { value = View.GONE}
    val passwordErrorVisibility = MutableLiveData<Int>().apply { value = View.GONE }

    //Input fields observers
    val userName = MutableLiveData<String>().apply{ observeForever(mUserNameObserver) }
    val password = MutableLiveData<String>().apply { observeForever(mPasswordObserver) }




    fun jumpToSignUp(){
        mNavigationCommandSender.value = NavigationCommand.ToSignUpFragment
    }

    //Input check methods
    private fun checkEmailIsValid(): Boolean = mUser.email.isNotEmpty()
    private fun checkPasswordIsValid(): Boolean = mUser.password.isNotEmpty()

    fun login(){

        var isInputValid = true

        if(!checkEmailIsValid()){
            isInputValid = false
            emailErrorVisibility.value = View.VISIBLE
        }else{
            emailErrorVisibility.value = View.INVISIBLE
        }

        if(!checkPasswordIsValid()){
            isInputValid = false
            passwordErrorVisibility.value = View.VISIBLE
        }else{
            passwordErrorVisibility.value = View.INVISIBLE
        }

        if(isInputValid){
            //TODO login to firebase
        }
    }

    override fun onCleared() {
        super.onCleared()
        userName.removeObserver(mUserNameObserver)
        password.removeObserver(mPasswordObserver)
    }

    sealed class NavigationCommand{
        object ToSignUpFragment : NavigationCommand()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel() as T
        }

    }
}
