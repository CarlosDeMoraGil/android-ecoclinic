package com.carlosdmg.ecoclinic.app.data

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.annotation.Single

@Single
class FirebaseAuthentication {

    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUserId(): String {
        return auth.currentUser?.uid.toString()
    }

    fun singOut(){
        auth.signOut()
    }


    fun createUser(
        email: String,
        password: String,
        onResult: (Result<AuthResult>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    onResult(Result.success(task.result))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Result<AuthResult>) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("@dev", "Login correcto ${user?.email}")
                    onResult(Result.success(task.result))
                } else {
                    Log.d("@dev", "Error ${task.exception}")
                    onResult(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }

}