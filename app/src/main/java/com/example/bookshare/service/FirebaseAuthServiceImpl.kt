package com.example.bookshare.service

import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseAuthServiceImpl(): FirebaseAuthService {

    private val mAuth by lazy{
        com.google.firebase.auth.FirebaseAuth.getInstance()
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult? {
        return try {
            mAuth.signInWithEmailAndPassword(email,password).await()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult? {
        return try {
            mAuth.createUserWithEmailAndPassword(email,password).await()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun reAuthCurrentUser(credential: AuthCredential): AuthResult? {
        return try {
            mAuth.currentUser!!.reauthenticateAndRetrieveData(credential).await()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }


    override fun logout() = mAuth.signOut()
    override fun currentUser() = mAuth.currentUser
}