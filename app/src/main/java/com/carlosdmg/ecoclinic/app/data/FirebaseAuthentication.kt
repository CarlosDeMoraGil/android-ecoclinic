package com.carlosdmg.ecoclinic.app.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.carlosdmg.ecoclinic.R
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.annotation.Single

@Single
class FirebaseAuthentication(private val context: Context) {

    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    fun signOut() {
        auth.signOut()
    }

    fun validateCredentials(
        email: String,
        password1: String,
        password2: String,
        phoneNumber: String
    ): List<Boolean> {
        val emailResult = validateEmail(email)
        val passwordResult = validatePasswords(password1, password2)
        val phoneNumberResult = validatePhoneNumber(phoneNumber)

        val credentialsResult = emailResult && passwordResult && phoneNumberResult

        return listOf(
            passwordResult, emailResult, credentialsResult, phoneNumberResult
        )
    }

    fun validateEmail(email: String): Boolean {
        if (email.isBlank()) return false

        val pattern = android.util.Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        if (phoneNumber.isBlank()) return false

        val pattern = android.util.Patterns.PHONE
        return pattern.matcher(phoneNumber).matches()
    }

    fun validatePasswords(pass1: String, pass2: String): Boolean {
        return pass1.isNotBlank() && pass1 == pass2
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
                    onResult(
                        Result.failure(
                            task.exception
                                ?: Exception(context.getString(R.string.signup_fr_unknown_error))
                        )
                    )
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
                    Toast.makeText(
                        context,
                        context.getString(R.string.signup_fr_login_succ), Toast.LENGTH_SHORT
                    ).show()
                    onResult(Result.success(task.result))
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.signup_fr_error), Toast.LENGTH_SHORT
                    ).show()
                    onResult(
                        Result.failure(
                            task.exception
                                ?: Exception(context.getString(R.string.signup_fr_unknown_error))
                        )
                    )
                }
            }
    }

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("@dev", "Email sent.")
            }
        }

    }

}