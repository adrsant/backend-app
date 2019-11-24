package com.luizalabs.infra.eai;

import com.luizalabs.exception.IntegrationException;
import java.net.URI;
import java.util.UUID;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class FindProductIntegration {

  //  @Value("${products-api.find-endpoint}")
  private String endpoint = "http://challenge-api.luizalabs.com/api/product/";
  @Autowired private RestTemplate template;

  @Transactional(propagation = Propagation.NEVER)
  public boolean execute(@NonNull final UUID productId) {
    URI uri = URI.create(endpoint + productId);
    HttpStatus statusCode = null;

    try {
      statusCode = template.getForEntity(uri, Object.class).getStatusCode();
    } catch (RestClientException ex) {
      log.warn("error on integration to products api");
    }

    if (statusCode.is5xxServerError()) {
      throw new IntegrationException();
    }

    return HttpStatus.OK == statusCode;
  }
}
