package com.example.lpuactivity.util

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

class FingerPrintManagementUtil(activity: FragmentActivity, executor: Executor, callBack:BiometricPrompt.AuthenticationCallback) {
    private var biometricPrompt = BiometricPrompt(activity, executor, callBack)

    fun showBiometricPrompt(){
        val prompt = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Please Verify")
            .setSubtitle("Accept Job")
            .setDescription("Confirm fingerprint to continue")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(prompt)
    }

}