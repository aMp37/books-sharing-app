package com.example.bookshare.viewmodel

import AuthRepositoryImpl
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshare.model.User
import com.example.bookshare.repository.AuthRepository
import com.example.bookshare.util.SingleLiveEvent
import com.example.bookshare.util.UiAvatarsUtil
import kotlinx.coroutines.launch

class ProfileEditViewModel : ViewModel() {

    val navigationCommandSender = SingleLiveEvent<NavigationCommand>()

    private val mAuthRepository: AuthRepository<User> by lazy { AuthRepositoryImpl }

    val editUser: MutableLiveData<User> by lazy { MutableLiveData<User>(mAuthRepository.getCurrentUser()) }

    val userOldPassword: MutableLiveData<String> = MutableLiveData()

    private fun jumpToProfileFragment(){
        navigationCommandSender.value = NavigationCommand.ToProfileFragment
    }

    fun saveChangesAndReturnToProfileFragment(){
        editUser.value?.let {

            //TODO check for different image source (camera/file)
            it.photoUrl = UiAvatarsUtil.getAvatarUrlByDisplayName(it.displayName)

            viewModelScope.launch {
                mAuthRepository.updateCurrentUser(userOldPassword.value?:"",it)
                jumpToProfileFragment()
            }
        }
    }

    sealed class NavigationCommand{
        object ToProfileFragment: NavigationCommand()
        object ToCameraActivity: NavigationCommand()
    }
}
