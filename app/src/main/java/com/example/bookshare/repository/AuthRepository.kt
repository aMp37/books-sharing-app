package com.example.bookshare.repository

import androidx.lifecycle.LiveData

interface AuthRepository<T> {
    val networkState: LiveData<NetworkState>
    suspend fun signInUser(user: T): Boolean
    suspend fun signUpUser(user: T): Boolean
    suspend fun updateCurrentUser(oldPassword: String, user: T): Boolean
    fun signOutUser()
    fun getCurrentUser(): T

    sealed class NetworkState{
        object Loading: NetworkState()
        object Success: NetworkState()
        data class Error(val errorCode: Int): NetworkState()
    }
}