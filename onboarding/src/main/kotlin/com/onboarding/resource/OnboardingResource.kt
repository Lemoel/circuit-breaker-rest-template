package com.onboarding.resource

import com.onboarding.configuration.LoggerProperties.logger
import com.onboarding.service.KycService
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class OnboardingResource(
    val kycService: KycService
) {
    @GetMapping("/{id}")
    fun saveOrder(@PathVariable("id") id: UUID) = ResponseEntity.ok(
        kycService.getProfile(id).also {
            logger.info("ONBOARDING - Get profile to $it")
        }
    )
}