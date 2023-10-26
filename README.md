# Circuit Breaker
Uma prova de conceito sobre circuit breaker usando **spring-boot e resilience4j**

**Link apresentacao:** https://docs.google.com/presentation/d/1FUo9OMCHZORgrsUe9wb8Gkv61aZfB7zO/edit?usp=sharing&ouid=115169692095866058473&rtpof=true&sd=true

**Resilience4j:** https://resilience4j.readme.io/

## Como testar

Utilizei *spring-boot* nos dois microservicos, então basta localizar os arquivos abaixo e executar na sua 
IDE favorita:

        1 - KycApplication.kt        
        2 - OnboardingApplication.kt

Depois basta realizar request para o endpoint:
http://localhost:8080/eaa2fb77-76b9-4b45-a396-ac588e2f00fe

#### *Acompanhe o log do microservico de **onboarding***

## Como funciona

**a)** O Circuit breaker esta configurado para monitorar as 8 últimas request;

**b)** Quando 4 delas falharem, o circuito será **aberto**;

**c)** Quando o circuito estiver **aberto**, as próximas request serão direcionadas para o **fallback**;

**d)** Após 1min, o circuito ira para o status de **half-open** e as próximas request serão direcionadas para o endpoint original;

**e)** Se a request falhar, o circuito volta para o status de **aberto**;

**f)** Se a request for bem sucedida, o circuito volta para o status de **fechado**;

**g)** O circuito só será **fechado** se 4 requests consecutivas forem bem sucedidas;

**h)** O circuito só será **aberto** se 4 requests consecutivas falharem;

**Podem acompanhar a troca de status nos logs do servico de onboarding.**

## Configuração do Circuit Breaker

Como o *circuit breaker* esta configurado no projeto de **onboarding**, destaco abaixo
as alteracoes realizadas para habilitar o *circuit breaker*. 

O projeto de *kyc* não precisa de alteracoes referente ao *circuit breaker*, ele simula o microservico com falhas.

### Dependências
Projeto de onboarding:

1) resilience4j-spring-boot2:2.1.0
2) spring-boot-starter-aop:3.1.5

### Configuração

**application.yaml** do projeto onboarding

```yaml
resilience4j.circuitbreaker:
  instances:
    onboardingCB:
      minimumNumberOfCalls: 4
      slidingWindowSize: 8
```

Aqui temos as principais partes da configuração do circuit-breaker: 

```kotlin

    @CircuitBreaker(name = "onboardingCB", fallbackMethod = "getProfileFallback")
    fun getProfile(id: UUID): String {
        return executeRequest(id)
    }
```

Método de **fallbackMethod**:

```kotlin
 fun getProfileFallback(id: UUID, e: Throwable): String {
    logger.error("Erro ao buscar perfil no KYC", e)
    logger.info("KYC OFF")
    logger.info("Buscando no cache")
    return CACHE.getOrDefault(id, "CACHE")
}
```