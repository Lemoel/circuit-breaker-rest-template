package com.kyc.resource

import com.kyc.service.KycService
import com.kyc.configuration.LoggerProperties.logger
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class KycResource(
    val kycService: KycService
) {
    @GetMapping("/{id}")
    fun saveOrder(@PathVariable("id") id: UUID): ResponseEntity<String> {
        val result = kycService.validateDocument(id).also {
            logger.info("KYC - Get profile to: $it")
        }
        return ResponseEntity.ok("id: $result")
    }
}