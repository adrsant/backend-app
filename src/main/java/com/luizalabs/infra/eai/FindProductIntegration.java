package com.luizalabs.infra.eai;

import com.luizalabs.exception.IntegrationException;
import java.net.URI;
import java.util.UUID;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class FindProductIntegration {

  @Value("${products-api.find-endpoint}")
  private String endpoint;

  @Autowired private RestTemplate template;

  @Transactional(propagation = Propagation.NEVER)
  @Retryable(
      maxAttempts = 2,
      value = IntegrationException.class,
      backoff = @Backoff(delay = 1500))
  public boolean execute(@NonNull final UUID productId) {
    URI uri = URI.create(endpoint + productId);
    HttpStatus statusCode = null;

    try {
      statusCode = template.getForEntity(uri, Object.class).getStatusCode();
    } catch (RestClientException ex) {
      if (!(ex instanceof HttpClientErrorException)) {
        log.warn("ERROR ON INTEGRATION TO PRODUCTS API", ex);
      }
    }

    if (statusCode == null || statusCode.is5xxServerError()) {
      throw new IntegrationException();
    }

    log.info("FOUND PRODUCT ID {} ON API", productId);

    return HttpStatus.OK == statusCode;
  }
}
