package com.onboarding.service

import com.onboarding.kyc.KycClientInterface
import java.util.*
import org.springframework.stereotype.Service

@Service
class KycService(private val kycClientImpl: KycClientInterface) {
    fun getProfile(id: UUID) = kycClientImpl.getProfile(id)
}
