package com.example.campustrack.validation

class RegistrationValidator : BaseValidator() {
    override fun validate(vararg params: String): ValidationResult {
        // Pastikan jumlah parameter sesuai
        if (params.size < 4) {
            return ValidationResult.Error("Parameter tidak lengkap")
        }

        val (email, password, confirmPassword, name) = params

        return when {
            !isNotEmpty(name) ->
                ValidationResult.Error("Nama tidak boleh kosong")
            !isNotEmpty(email) ->
                ValidationResult.Error("Email tidak boleh kosong")
            !isNotEmpty(password) ->
                ValidationResult.Error("Password tidak boleh kosong")
            !isValidEmail(email) ->
                ValidationResult.Error("Format email tidak valid")
            password != confirmPassword ->
                ValidationResult.Error("Konfirmasi password tidak cocok")
            else -> ValidationResult.Success
        }
    }
}