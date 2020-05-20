package com.example.bookshare.viewmodel

import AuthRepositoryImpl
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.bookshare.model.User
import com.example.bookshare.repository.AuthRepository
import com.example.bookshare.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private val mNavigationCommandSender = SingleLiveEvent<NavigationCommand>()
    private val mUser = User()

    private val mAuthRepository: AuthRepository<User> by lazy { AuthRepositoryImpl() }

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

    val loadingState = mAuthRepository.networkState

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
            viewModelScope.launch {
                if(mAuthRepository.signInUser(mUser)) {
                    Log.d("SIGNIN", "OK")
                    withContext(Main) {
                        mNavigationCommandSender.value = NavigationCommand.ToMainActivity
                    }
                }
                else
                    Log.d("SIGNIN","Error!")
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        userName.removeObserver(mUserNameObserver)
        password.removeObserver(mPasswordObserver)
    }

    sealed class NavigationCommand{
        object ToSignUpFragment : NavigationCommand()
        object ToMainActivity: NavigationCommand()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel() as T
        }

    }
}
