package com.example.bookshare.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bookshare.util.SingleLiveEvent

class ProfileEditViewModel : ViewModel() {

    val navigationCommandSender = SingleLiveEvent<NavigationCommand>()

    fun jumpToProfileFragment(){
        navigationCommandSender.value = NavigationCommand.ToProfileFragment
    }

    sealed class NavigationCommand{
        object ToProfileFragment: NavigationCommand()
        object ToCameraActivity: NavigationCommand()
    }
}
