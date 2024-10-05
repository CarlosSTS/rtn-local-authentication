package com.rtnlocalauthentication;

import com.rtnlocalauthentication.NativeGetLocalAuthenticationSpec
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.turbomodule.core.interfaces.TurboModule

class LocalAuthenticationModule(reactContext: ReactApplicationContext) : NativeGetLocalAuthenticationSpec(reactContext) {

    companion object {
        const val NAME = "RTNLocalAuthentication"
        private const val AUTH_REQUEST = 18864
        private const val E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST"
        private const val E_AUTH_CANCELLED = "E_AUTH_CANCELLED"
        private const val E_FAILED_TO_SHOW_AUTH = "E_FAILED_TO_SHOW_AUTH"
        private const val E_ONE_REQ_AT_A_TIME = "E_ONE_REQ_AT_A_TIME"
        private const val WITHOUT_AUTENTICATION = "WITHOUT_AUTENTICATION"
    }

    private var mKeyguardManager: KeyguardManager = reactContext.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    private var authPromise: Promise? = null

    private val mActivityEventListener: ActivityEventListener = object : BaseActivityEventListener() {
        override fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode != AUTH_REQUEST || authPromise == null) return

            when (resultCode) {
                Activity.RESULT_CANCELED -> {
                    authPromise?.reject(E_AUTH_CANCELLED, "User canceled the authentication.")
                }
                Activity.RESULT_OK -> {
                    authPromise?.resolve(true)
                }
                else -> {
                    authPromise?.reject(E_FAILED_TO_SHOW_AUTH, "Unknown result code: $resultCode")
                }
            }
            authPromise = null
        }
    }

    init {
        reactContext.addActivityEventListener(mActivityEventListener)
    }

    override fun getName() = NAME

    override fun isDeviceSecure(promise: Promise) {
        promise.resolve(mKeyguardManager.isDeviceSecure)
    }

    override fun authenticate(map: ReadableMap, promise: Promise) {
        val currentActivity = currentActivity

        if (authPromise != null) {
            promise.reject(E_ONE_REQ_AT_A_TIME, "Authentication already in progress.")
            return
        }

        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity does not exist.")
            return
        }

        if (!mKeyguardManager.isDeviceSecure) {
            promise.resolve(WITHOUT_AUTENTICATION)
            return
        }

        authPromise = promise

        val reason = if (map.hasKey("reason")) map.getString("reason") else null
        val description = if (map.hasKey("description")) map.getString("description") else null

        try {
            val authIntent = mKeyguardManager.createConfirmDeviceCredentialIntent(reason, description)
            if (authIntent != null) {
                currentActivity.startActivityForResult(authIntent, AUTH_REQUEST)
            } else {
                authPromise?.reject(E_FAILED_TO_SHOW_AUTH, "Failed to create authentication intent.")
                authPromise = null
            }
        } catch (e: Exception) {
            authPromise?.reject(E_FAILED_TO_SHOW_AUTH, e.message)
            authPromise = null
        }
    }
}
