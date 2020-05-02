package com.example.bookshare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignUpViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    @Suppress("UNCHECKED_CAST")
    class Factory: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SignUpViewModel() as T
        }

    }
}
