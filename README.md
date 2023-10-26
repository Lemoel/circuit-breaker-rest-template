# circuit-breaker-rest-template
Uma prova de conceito sobre circuit-breaker com resilience4j


## Como testar 
Realize uma request para o endpoint
http://localhost:8080/eaa2fb77-76b9-4b45-a396-ac588e2f00fe

#### *Acompanhe o log do microservico de **onboarding***

**a)** O Circuit breaker esta configurado para monitorar as 8 últimas request;

**b)** Quando 4 delas falharem, o circuito será **aberto**;

**c)** Quando o circuito estiver **aberto**, as próximas request serão direcionadas para o **fallback**;

**d)** Após 1min, o circuito ira para o status de **half-open** e as próximas request serão direcionadas para o endpoint original;

**e)** Se a request falhar, o circuito volta para o status de **aberto**;

**f)** Se a request for bem sucedida, o circuito volta para o status de **fechado**;

**g)** O circuito só será **fechado** se 4 requests consecutivas forem bem sucedidas;

**h)** O circuito só será **aberto** se 4 requests consecutivas falharem;

**Podem acompanhar a troca de status no log do onboarding.**



# 