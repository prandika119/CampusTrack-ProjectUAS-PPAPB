package com.example.campustrack.validation

class LoginValidator : BaseValidator() {
    override fun validate(vararg params: String): ValidationResult {
        // Pastikan jumlah parameter sesuai
        if (params.size < 2) {
            return ValidationResult.Error("Parameter tidak lengkap")
        }

        val (email, password) = params

        return when {
            !isNotEmpty(email) ->
                ValidationResult.Error("Email tidak boleh kosong")
            !isNotEmpty(password) ->
                ValidationResult.Error("Password tidak boleh kosong")
            !isValidEmail(email) ->
                ValidationResult.Error("Format email tidak valid")
            else -> ValidationResult.Success
        }
    }
}