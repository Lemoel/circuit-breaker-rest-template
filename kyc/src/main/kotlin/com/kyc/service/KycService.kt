package com.kyc.service

import java.util.UUID
import org.springframework.stereotype.Service

@Service
class KycService {
    fun validateDocument(document: UUID) = document.toString() + " - KYC"
}