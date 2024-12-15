package com.example.campustrack.validation

import android.util.Patterns

abstract class BaseValidator {
    // Fungsi validasi email
    protected fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Fungsi validasi field tidak kosong
    protected fun isNotEmpty(value: String): Boolean {
        return value.trim().isNotEmpty()
    }

    // Abstract method untuk validasi spesifik
    abstract fun validate(vararg params: String): ValidationResult
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}