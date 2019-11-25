package com.luizalabs.infra.eai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.google.common.base.Charsets;
import com.luizalabs.test.AbstractIntegrationTest;
import java.net.URI;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

public class FindProductIntegrationTest extends AbstractIntegrationTest {

  @Value("${products-api.find-endpoint}")
  private String endpoint;

  @SpyBean private FindProductIntegration integration;

  @Autowired private RestTemplate template;

  @Test
  public void should_find_product() throws Exception {
    UUID productId = UUID.randomUUID();
    var server = mockGetOK(productId);
    boolean exist = integration.execute(productId);

    assertThat(exist).isTrue();
    server.verify();
  }

  @Test
  public void should_not_found_product() throws Exception {
    UUID productId = UUID.randomUUID();
    var server = mockGetNotFound(productId);
    boolean exist = integration.execute(productId);

    assertThat(exist).isFalse();
    server.verify();
  }

  @Test
  public void should_not_found_product_error() throws Exception {
    UUID productId = UUID.randomUUID();
    var server = mockGetError(productId);
    boolean exist = integration.execute(productId);

    then(integration).should().onError(productId);
    assertThat(exist).isFalse();
    server.verify();
  }

  private MockRestServiceServer mockGetOK(UUID uuid) throws Exception {
    String json =
        StreamUtils.copyToString(
            getClass().getResourceAsStream("/json_mock/rs_find_product.json"), Charsets.UTF_8);

    return configureMock(uuid, HttpMethod.GET, json, HttpStatus.OK);
  }

  private MockRestServiceServer mockGetNotFound(UUID uuid) throws Exception {
    return configureMock(uuid, HttpMethod.GET, "", HttpStatus.NOT_FOUND);
  }

  private MockRestServiceServer mockGetError(UUID uuid) throws Exception {
    return configureMock(uuid, HttpMethod.GET, "", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private MockRestServiceServer configureMock(
      UUID uuid, HttpMethod method, String body, HttpStatus status) throws Exception {

    MockRestServiceServer mockServer = MockRestServiceServer.createServer(template);
    mockServer
        .expect(ExpectedCount.times(1), requestTo(new URI(endpoint + uuid)))
        .andExpect(method(method))
        .andRespond(withStatus(status).contentType(MediaType.APPLICATION_JSON_UTF8).body(body));

    return mockServer;
  }
}
