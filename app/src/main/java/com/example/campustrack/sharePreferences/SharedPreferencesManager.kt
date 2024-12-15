package com.example.campustrack.sharePreferences

import android.content.Context

class SharedPreferencesManager (context: Context) {
    private val SP_ID = "SpId"
    private val SP_NAMA = "SpNama"
    private val SP_EMAIL = "SpEmail"
    private val SP_PRODI = "SpProdi"
    private val SP_ANGKATAN = "SpAngkatan"
    private val SP_ISLOGIN = "SpIsLogin"

    private val sharedPreferences = context.getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
    private val spEditor = sharedPreferences.edit()

    fun saveString(key: String, value: String) {
        spEditor.putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun saveInt(key: String, value: Int) {
        spEditor.putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun saveBoolean(key: String, value: Boolean) {
        spEditor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clear() {
        spEditor.clear().apply()
    }

    fun saveName(value: String) {
        spEditor.putString(SP_NAMA, value).apply()
    }
    fun saveEmail(value: String) {
        spEditor.putString(SP_EMAIL, value).apply()
    }
    fun saveProdi(value: String) {
        spEditor.putString(SP_PRODI, value).apply()
    }
    fun saveAngkatan(value: Int) {
        spEditor.putInt(SP_ANGKATAN, value).apply()
    }
    fun saveId(value: String){
        spEditor.putString(SP_ID, value).apply()
    }

    fun getName(): String? {
        return sharedPreferences.getString(SP_NAMA, "")
    }
    fun isLogin(): Boolean {
        return sharedPreferences.getBoolean(SP_ISLOGIN, false)
    }
    fun getProdi(): String? {
        return sharedPreferences.getString(SP_PRODI, "")
    }
    fun getEmail(): String? {
        return sharedPreferences.getString(SP_EMAIL, "")
    }
    fun getAngkatan(): Int {
        return sharedPreferences.getInt(SP_ANGKATAN, 0)
    }
}