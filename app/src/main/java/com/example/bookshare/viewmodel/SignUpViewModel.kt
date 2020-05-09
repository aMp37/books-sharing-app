package com.example.bookshare.viewmodel

import AuthRepositoryImpl
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.bookshare.model.User
import com.example.bookshare.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val mUser = User()
    private val mNavigationCommandSender = MutableLiveData<NavigationCommand>()

    private val mAuthRepository: AuthRepository<User> by lazy { AuthRepositoryImpl() }


    private val mEmailObserver = Observer<String>{
        Log.d("SET",it)
        mUser.email = it
    }

    private val mPasswordObserver = Observer<String>{
        Log.d("SET",it)
        mUser.password = it
    }

    private val mDisplayNameObserver = Observer<String>{
        Log.d("SET",it)
        mUser.displayName = it
    }


    // Error messages visibility
    val emailErrorVisibility = MutableLiveData<Int>().apply { value = View.GONE}
    val passwordErrorVisibility = MutableLiveData<Int>().apply { value = View.GONE }
    val displayNameErrorVisibility = MutableLiveData<Int>().apply { value = View.GONE}


    // Input fields observers
    val email = MutableLiveData<String>().apply { observeForever(mEmailObserver) }
    val password = MutableLiveData<String>().apply{ observeForever(mPasswordObserver) }
    val displayName = MutableLiveData<String>().apply { observeForever(mDisplayNameObserver) }


    //Input check methods
    private fun checkEmailIsValid(): Boolean = mUser.email.isNotEmpty()
    private fun checkPasswordIsValid(): Boolean = mUser.password.isNotEmpty()
    private fun checkDisplayNameIsValid(): Boolean = mUser.displayName.isNotEmpty()


    fun confirmAccount(){

        var isInputValid = true

        if(!checkEmailIsValid()){
            Log.d("SET","badEmail")
            emailErrorVisibility.value = View.VISIBLE
            isInputValid = false
        }else{
            emailErrorVisibility.value = View.INVISIBLE
        }

        if(!checkPasswordIsValid()){
            Log.d("SET","badPassword")
            passwordErrorVisibility.value = View.VISIBLE
            isInputValid = false
        }else{
            passwordErrorVisibility.value = View.INVISIBLE
        }

        if(!checkDisplayNameIsValid()){
            Log.d("SET","badDisplayName")
            displayNameErrorVisibility.value = View.VISIBLE
            isInputValid = false
        }else{
            displayNameErrorVisibility.value = View.INVISIBLE
        }

        if(isInputValid){

            viewModelScope.launch {
                if(mAuthRepository.signUpUser(mUser))
                    Log.d("SIGNUP","OK")
                else
                    Log.d("SIGNUP","Error!")

            }
        }

    }

    override fun onCleared() {
        super.onCleared()

        email.removeObserver(mEmailObserver)
        password.removeObserver(mPasswordObserver)
        displayName.removeObserver(mDisplayNameObserver)
    }


    sealed class NavigationCommand{
        object ToLoginUpFragment : NavigationCommand()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SignUpViewModel() as T
        }

    }
}
