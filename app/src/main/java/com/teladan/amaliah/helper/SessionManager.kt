package com.teladan.amaliah.helper

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    companion object {
        private const val PREF_NAME = "SPKSiswaTeladanPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_ADMIN_NAME = "adminName"
    }

    fun saveLoginSession(adminName: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_ADMIN_NAME, adminName)
        editor.apply() // .apply() berjalan asynchronous (lebih disarankan daripada .commit())
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getAdminName(): String? {
        return prefs.getString(KEY_ADMIN_NAME, null)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}
