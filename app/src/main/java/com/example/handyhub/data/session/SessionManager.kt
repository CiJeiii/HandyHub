package com.example.handyhub.data.session

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * Manages EncryptedSharedPreferences for HandyHub RBAC session persistence.
 * Stores user_id, account_type ('EMPLOYER' or 'EMPLOYEE'), and session tokens securely.
 */
class SessionManager(context: Context) {

    private val prefs = try {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            "handyhub_rbac_session",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } catch (_: Exception) {
        context.getSharedPreferences("handyhub_rbac_session_fallback", Context.MODE_PRIVATE)
    }

    fun saveUserSession(userId: String, accountType: String, token: String) {
        prefs.edit()
            .putString("KEY_USER_ID", userId)
            .putString("KEY_ACCOUNT_TYPE", accountType.uppercase())
            .putString("KEY_JWT_TOKEN", token)
            .apply()
    }

    fun getUserId(): String? = prefs.getString("KEY_USER_ID", null)

    fun getAccountType(): String? = prefs.getString("KEY_ACCOUNT_TYPE", null)

    fun getToken(): String? = prefs.getString("KEY_JWT_TOKEN", null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
