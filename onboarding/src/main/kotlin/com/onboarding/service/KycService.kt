package com.onboarding.service

import com.onboarding.kyc.KycClient
import java.util.*
import org.springframework.stereotype.Service

@Service
class KycService(private val kycClientImpl: KycClient) {
    fun getProfile(id: UUID) = kycClientImpl.getProfile(id)
}
