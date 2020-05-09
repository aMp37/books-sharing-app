package com.example.bookshare.repository

interface AuthRepository<T> {
    suspend fun signInUser(user: T): Boolean
    suspend fun signUpUser(user: T): Boolean
    suspend fun updateCurrentUser(oldPassword: String, user: T): Boolean
    fun signOutUser()
    fun getCurrentUser(): T

}