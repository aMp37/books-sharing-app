package com.example.bookshare.service

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

interface FirebaseAuthService {
    suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult?
    suspend fun registerWithEmailAndPassword(email: String, password: String): AuthResult?
    suspend fun reAuthCurrentUser(credential: AuthCredential): AuthResult?
    fun logout()
    fun currentUser(): FirebaseUser?
}