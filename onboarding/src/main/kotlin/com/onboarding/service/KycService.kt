package com.onboarding.service

import com.onboarding.kyc.KycClientImpl
import java.util.*
import org.springframework.stereotype.Service

@Service
class KycService(private val kycClientImpl: KycClientImpl) {
    fun getProfile(id: UUID) = kycClientImpl.getProfile(id)
}
