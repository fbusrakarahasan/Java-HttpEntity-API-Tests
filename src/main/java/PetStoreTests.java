import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.baseURI;

public class PetStoreTests {

    HttpHeaders headers;
    RestTemplate restTemplate;

    public PetStoreTests() {
        baseURI = "https://petstore.swagger.io/v2/store";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate = new RestTemplate();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    public void PlaceAnOrderForAPet() throws JsonProcessingException {
        String requestUrl = String.format("%s/order/", baseURI);

        String postData = "{\n" +
                "    \"id\": 5,\n" +
                "    \"petId\": 15,\n" +
                "    \"quantity\": 35,\n" +
                "    \"shipDate\": \"2022-10-05T01:22:06.626Z\",\n" +
                "    \"status\": \"avaiable\",\n" +
                "    \"complete\": true\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(postData, headers);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, request, String.class);
        String responseStr = restTemplate.postForObject(requestUrl, request, String.class);
        JsonNode JsonResponse = objectMapper.readTree(responseStr);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals("OK", response.getStatusCode().name());

        Assert.assertNotNull(JsonResponse);
        Assert.assertNotNull(JsonResponse.path("id").asText());
        Assert.assertNotNull(JsonResponse.path("petId").asText());
        Assert.assertNotNull(JsonResponse.path("quantity").asText());
        Assert.assertNotNull(JsonResponse.path("shipDate").asText());
        Assert.assertNotNull(JsonResponse.path("status").asText());
        Assert.assertNotNull(JsonResponse.path("complete").asText());

        Assert.assertEquals(6, JsonResponse.size()); // Response Key Boyutu
        Assert.assertEquals("5", JsonResponse.path("id").asText());
        Assert.assertEquals("15", JsonResponse.path("petId").asText());
        Assert.assertEquals("35", JsonResponse.path("quantity").asText());
        Assert.assertEquals("2022-10-05T01:22:06.626+0000", JsonResponse.path("shipDate").asText());
        Assert.assertEquals("avaiable", JsonResponse.path("status").asText());
        Assert.assertEquals("true", JsonResponse.path("complete").asText());

        Assert.assertEquals("[application/json]", response.getHeaders().getValuesAsList("Content-Type").toString()); // Content-Type i??eriyor mu?
        Assert.assertEquals("[chunked]", response.getHeaders().getValuesAsList("Transfer-Encoding").toString()); // Transfer-Encoding i??eriyor mu?
        Assert.assertEquals("[keep-alive]", response.getHeaders().getValuesAsList("Connection").toString()); // Connection i??eriyor mu?
        Assert.assertEquals("[*]", response.getHeaders().getValuesAsList("Access-Control-Allow-Origin").toString()); // Access-Control-Allow-Origin i??eriyor mu?
        Assert.assertEquals("[GET, POST, DELETE, PUT]", response.getHeaders().getValuesAsList("Access-Control-Allow-Methods").toString()); // Access-Control-Allow-Methods i??eriyor mu?
        Assert.assertEquals("[Content-Type, api_key, Authorization]", response.getHeaders().getValuesAsList("Access-Control-Allow-Headers").toString()); // Access-Control-Allow-Headers i??eriyor mu?
        Assert.assertEquals("[Jetty(9.2.9.v20150224)]", response.getHeaders().getValuesAsList("Server").toString()); // Server i??eriyor mu?
    }

    @Test
    @Order(2)
    public void FindPurchaseOrderByID() throws JsonProcessingException {
        String requestUrl = String.format("%s/order/5/", baseURI);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        String responseStr = restTemplate.getForObject(requestUrl, String.class);
        JsonNode JsonResponse = objectMapper.readTree(responseStr);

        Assert.assertNotNull(response.getBody()); // Body null kontrol??

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals("OK", response.getStatusCode().name());

        Assert.assertNotNull(JsonResponse);
        Assert.assertNotNull(JsonResponse.path("id").asText());
        Assert.assertNotNull(JsonResponse.path("petId").asText());
        Assert.assertNotNull(JsonResponse.path("quantity").asText());
        Assert.assertNotNull(JsonResponse.path("shipDate").asText());
        Assert.assertNotNull(JsonResponse.path("status").asText());
        Assert.assertNotNull(JsonResponse.path("complete").asText());

        Assert.assertEquals(6, JsonResponse.size()); // Response Key Boyutu
        Assert.assertEquals(5, JsonResponse.path("id").asInt());
        Assert.assertEquals(15, JsonResponse.path("petId").asInt());
        Assert.assertEquals(35, JsonResponse.path("quantity").asInt());
        Assert.assertEquals("2022-10-05T01:22:06.626+0000", JsonResponse.path("shipDate").asText());
        Assert.assertEquals("avaiable", JsonResponse.path("status").asText());
        Assert.assertTrue(JsonResponse.path("complete").asBoolean());

        Assert.assertEquals("[application/json]", response.getHeaders().getValuesAsList("Content-Type").toString()); // Content-Type i??eriyor mu?
        Assert.assertEquals("[chunked]", response.getHeaders().getValuesAsList("Transfer-Encoding").toString()); // Transfer-Encoding i??eriyor mu?
        Assert.assertEquals("[keep-alive]", response.getHeaders().getValuesAsList("Connection").toString()); // Connection i??eriyor mu?
        Assert.assertEquals("[*]", response.getHeaders().getValuesAsList("Access-Control-Allow-Origin").toString()); // Access-Control-Allow-Origin i??eriyor mu?
        Assert.assertEquals("[GET, POST, DELETE, PUT]", response.getHeaders().getValuesAsList("Access-Control-Allow-Methods").toString()); // Access-Control-Allow-Methods i??eriyor mu?
        Assert.assertEquals("[Content-Type, api_key, Authorization]", response.getHeaders().getValuesAsList("Access-Control-Allow-Headers").toString()); // Access-Control-Allow-Headers i??eriyor mu?
        Assert.assertEquals("[Jetty(9.2.9.v20150224)]", response.getHeaders().getValuesAsList("Server").toString()); // Server i??eriyor mu?
    }

    @Test
    @Order(3)
    public void DeletePurchaseOrderByID() throws JsonProcessingException {
        String requestUrl = String.format("%s/order/5/", baseURI);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.DELETE, request, String.class);
        JsonNode JsonResponse = objectMapper.readTree(response.getBody());

        Assert.assertNotNull(response.getBody()); // Body null kontrol??

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals("OK", response.getStatusCode().name());

        Assert.assertNotNull(JsonResponse.path("code").asText());
        Assert.assertNotNull(JsonResponse.path("type").asText());
        Assert.assertNotNull(JsonResponse.path("message").asText());

        Assert.assertEquals(3, JsonResponse.size()); // Response Key Boyutu
        Assert.assertEquals(200,JsonResponse.path("code").asInt());
        Assert.assertEquals("unknown",JsonResponse.path("type").asText());
        Assert.assertEquals("5",JsonResponse.path("message").asText());

        Assert.assertEquals("[application/json]", response.getHeaders().getValuesAsList("Content-Type").toString()); // Content-Type i??eriyor mu?
        Assert.assertEquals("[chunked]", response.getHeaders().getValuesAsList("Transfer-Encoding").toString()); // Transfer-Encoding i??eriyor mu?
        Assert.assertEquals("[keep-alive]", response.getHeaders().getValuesAsList("Connection").toString()); // Connection i??eriyor mu?
        Assert.assertEquals("[*]", response.getHeaders().getValuesAsList("Access-Control-Allow-Origin").toString()); // Access-Control-Allow-Origin i??eriyor mu?
        Assert.assertEquals("[GET, POST, DELETE, PUT]", response.getHeaders().getValuesAsList("Access-Control-Allow-Methods").toString()); // Access-Control-Allow-Methods i??eriyor mu?
        Assert.assertEquals("[Content-Type, api_key, Authorization]", response.getHeaders().getValuesAsList("Access-Control-Allow-Headers").toString()); // Access-Control-Allow-Headers i??eriyor mu?
        Assert.assertEquals("[Jetty(9.2.9.v20150224)]", response.getHeaders().getValuesAsList("Server").toString()); // Server i??eriyor mu?
    }

    @Test
    @Order(4)
    public void ReturnsPetInventoriesByStatus() throws JsonProcessingException {
        String requestUrl = String.format("%s/inventory/", baseURI);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        String responseStr = restTemplate.getForObject(requestUrl, String.class);
        JsonNode JsonResponse = objectMapper.readTree(responseStr);

        Assert.assertNotNull(response.getBody()); // Body null kontrol??

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals("OK", response.getStatusCode().name());

        Assert.assertNotNull(JsonResponse);

        Assert.assertEquals("[application/json]", response.getHeaders().getValuesAsList("Content-Type").toString()); // Content-Type i??eriyor mu?
        Assert.assertEquals("[chunked]", response.getHeaders().getValuesAsList("Transfer-Encoding").toString()); // Transfer-Encoding i??eriyor mu?
        Assert.assertEquals("[keep-alive]", response.getHeaders().getValuesAsList("Connection").toString()); // Connection i??eriyor mu?
        Assert.assertEquals("[*]", response.getHeaders().getValuesAsList("Access-Control-Allow-Origin").toString()); // Access-Control-Allow-Origin i??eriyor mu?
        Assert.assertEquals("[GET, POST, DELETE, PUT]", response.getHeaders().getValuesAsList("Access-Control-Allow-Methods").toString()); // Access-Control-Allow-Methods i??eriyor mu?
        Assert.assertEquals("[Content-Type, api_key, Authorization]", response.getHeaders().getValuesAsList("Access-Control-Allow-Headers").toString()); // Access-Control-Allow-Headers i??eriyor mu?
        Assert.assertEquals("[Jetty(9.2.9.v20150224)]", response.getHeaders().getValuesAsList("Server").toString()); // Server i??eriyor mu?
    }
}