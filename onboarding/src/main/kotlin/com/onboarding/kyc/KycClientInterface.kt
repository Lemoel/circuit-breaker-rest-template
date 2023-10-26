package com.onboarding.kyc

import java.util.*

interface KycClientInterface {
    fun getProfile(id: UUID): String
}