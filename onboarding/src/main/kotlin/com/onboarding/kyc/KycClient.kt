package com.onboarding.kyc

import com.onboarding.configuration.LoggerProperties.logger
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.collections.HashMap

@Service
class KycClient(
    val restTemplate: RestTemplate
) {

    private val CACHE: MutableMap<UUID, String> = HashMap()

    @CircuitBreaker(name = "onboardingCB", fallbackMethod = "getProfileFallback")
    fun getProfile(id: UUID): String {
        return executeRequest(id)
    }

    private fun executeRequest(id: UUID): String {

        val urlKyc = "http://localhost:8082/kyc/profile/"
        logger.info("### - Inicio da request ao KYC - ###")

        return try {
            val result = restTemplate.getForObject(urlKyc + id, String::class.java)
            logger.info("Successo na request a KYC")
            val cachedResult = result.toString() + " - CACHE"
            CACHE[id] = cachedResult
            cachedResult
        } catch (e: Exception) {
            logger.error("Erro ao buscar perfil no KYC")
            throw e
        }

    }

    fun getProfileFallback(id: UUID, e: Throwable): String {
        logger.error("Erro ao buscar perfil no KYC", e)
        logger.info("KYC OFF")
        logger.info("Buscando no cache")
        return CACHE.getOrDefault(id, "CACHE")
    }

}


