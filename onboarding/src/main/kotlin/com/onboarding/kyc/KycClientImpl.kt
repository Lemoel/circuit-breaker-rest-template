package com.onboarding.kyc

import com.onboarding.configuration.LoggerProperties.logger
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.collections.HashMap

@Service
class KycClientImpl(
    val restTemplate: RestTemplate
): KycClientInterface {

    private val CACHE: MutableMap<UUID, String> = HashMap()

    @CircuitBreaker(name = "onboardingCB", fallbackMethod = "getProfileFallback")
    override fun getProfile(id: UUID): String {
        return executeRequest(id)
    }

    private fun executeRequest(id: UUID): String {

        val url = "http://localhost:8082/kyc/profile/"
        logger.info("### - Inicio da request ao KYC - ###")

        val result = try {
            restTemplate.getForObject(url + id, String::class.java)
        } catch (e: Exception) {
            logger.error("Erro ao buscar perfil no KYC")
            throw e
        }

        logger.info("Successo na request a KYC")
        logger.info("Alimentando o cache")

        //Gerando um cache em memória
        CACHE[id] = result.toString() + " - CACHE"

        return result.toString()
    }

    fun getProfileFallback(id: UUID, e: Throwable): String {
        logger.info("KYC OFF")
        logger.info("Buscando no cache")
        return CACHE.getOrDefault(id, "CACHE" )
    }

}


