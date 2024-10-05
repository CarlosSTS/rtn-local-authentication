package com.rtnlocalauthentication;

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.module.model.ReactModuleInfo

class LocalAuthenticationPackage : TurboReactPackage() {
    override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
        return if (name == LocalAuthenticationModule.NAME) {
            LocalAuthenticationModule(reactContext)
        } else {
            null
        }
    }

    override fun getReactModuleInfoProvider() = ReactModuleInfoProvider {
        mapOf(
            LocalAuthenticationModule.NAME to ReactModuleInfo(
                LocalAuthenticationModule.NAME,
                LocalAuthenticationModule.NAME,
                false, // canOverrideExistingModule
                false, // needsEagerInit
                true, // hasConstants
                false, // isCxxModule
                true // isTurboModule
            )
        )
    }
}