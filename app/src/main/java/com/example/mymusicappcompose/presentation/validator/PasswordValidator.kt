package com.example.mymusicappcompose.presentation.validator

class PasswordValidator {

    private val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}\$"

    fun isValid(password: String): Boolean {
        return password.matches(regex.toRegex())
    }

    fun isValidAndEqual(password: String, confirmationPassword: String): Boolean {
        if (!isValid(password)) {
            return false
        }
        if (!isValid(confirmationPassword)) {
            return false
        }
        return password == confirmationPassword
    }
}