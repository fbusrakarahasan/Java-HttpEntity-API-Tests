<h1 align="center"> :bookmark_tabs: Enuygun - Test Automation Bootcamp - HW4 </h1>
 

> ##  ``` HttpEntity - PetStore API Testi   ``` 
 
https://petstore.swagger.io/#/store/ store path endpointlerin API testinin yazılması.

```java
HttpHeaders headers;
RestTemplate restTemplate;

public PetStoreTests() {
  baseURI = "https://petstore.swagger.io/v2/store";
  headers = new HttpHeaders();
  headers.setContentType(MediaType.APPLICATION_JSON);
  restTemplate = new RestTemplate();
}

private final ObjectMapper objectMapper = new ObjectMapper();
``` 
    
## ✅ Place an order for a pet 
https://petstore.swagger.io/v2/store/order

PostData

```json
{
    "id": 5,
    "petId": 15,
    "quantity": 35,
    "shipDate": "2022-10-05T01:22:06.626Z",
    "status": "avaiable",
    "complete": true
}
``` 
 
<details>
  <summary><h4>Testi görüntülemek için tıklayın</h4></summary>
  
```java
@Test
@Order(1)
public void PlaceAnOrderForAPet() throws JsonProcessingException {
  String requestUrl = String.format("%s/order/", baseURI);

  String postData = "{\n" +
                      "\"id\": 5,\n" +
                      "\"petId\": 15,\n" +
                      "\"quantity\": 35,\n" +
                      "\"shipDate\": \"2022-10-05T01:22:06.626Z\",\n" +
                      "\"status\": \"avaiable\",\n" +
                      "\"complete\": true\n" +
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

  Assert.assertEquals("[application/json]", response.getHeaders().getValuesAsList("Content-Type").toString()); // Content-Type içeriyor mu?
  Assert.assertEquals("[chunked]", response.getHeaders().getValuesAsList("Transfer-Encoding").toString()); // Transfer-Encoding içeriyor mu?
  Assert.assertEquals("[keep-alive]", response.getHeaders().getValuesAsList("Connection").toString()); // Connection içeriyor mu?
  Assert.assertEquals("[*]", response.getHeaders().getValuesAsList("Access-Control-Allow-Origin").toString()); // Access-Control-Allow-Origin içeriyor mu?
  Assert.assertEquals("[GET, POST, DELETE, PUT]", response.getHeaders().getValuesAsList("Access-Control-Allow-Methods").toString()); // Access-Control-Allow-Methods içeriyor mu?
  Assert.assertEquals("[Content-Type, api_key, Authorization]", response.getHeaders().getValuesAsList("Access-Control-Allow-Headers").toString()); // Access-Control-Allow-Headers içeriyor mu?
  Assert.assertEquals("[Jetty(9.2.9.v20150224)]", response.getHeaders().getValuesAsList("Server").toString()); // Server içeriyor mu?
}
```
</details>

## ✅ Find purchase order by ID
https://petstore.swagger.io/v2/store/order/5

<details>
  <summary><h4>Testi görüntülemek için tıklayın</h4></summary>
  
```java
@Test
@Order(2)
public void FindPurchaseOrderByID() throws JsonProcessingException {
  String requestUrl = String.format("%s/order/5/", baseURI);

  HttpEntity<String> request = new HttpEntity<>(headers);
  ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
  String responseStr = restTemplate.getForObject(requestUrl, String.class);
  JsonNode JsonResponse = objectMapper.readTree(responseStr);

  Assert.assertNotNull(response.getBody()); // Body null kontrolü

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

  Assert.assertEquals("[application/json]", response.getHeaders().getValuesAsList("Content-Type").toString()); // Content-Type içeriyor mu?
  Assert.assertEquals("[chunked]", response.getHeaders().getValuesAsList("Transfer-Encoding").toString()); // Transfer-Encoding içeriyor mu?
  Assert.assertEquals("[keep-alive]", response.getHeaders().getValuesAsList("Connection").toString()); // Connection içeriyor mu?
  Assert.assertEquals("[*]", response.getHeaders().getValuesAsList("Access-Control-Allow-Origin").toString()); // Access-Control-Allow-Origin içeriyor mu?
  Assert.assertEquals("[GET, POST, DELETE, PUT]", response.getHeaders().getValuesAsList("Access-Control-Allow-Methods").toString()); // Access-Control-Allow-Methods içeriyor mu?
  Assert.assertEquals("[Content-Type, api_key, Authorization]", response.getHeaders().getValuesAsList("Access-Control-Allow-Headers").toString()); // Access-Control-Allow-Headers içeriyor mu?
  Assert.assertEquals("[Jetty(9.2.9.v20150224)]", response.getHeaders().getValuesAsList("Server").toString()); // Server içeriyor mu?
}
```
</details>

## ✅ Delete purchase order by ID
https://petstore.swagger.io/v2/store/order/5

<details>
  <summary><h4>Testi görüntülemek için tıklayın</h4></summary>
  
```java
@Test
@Order(3)
public void DeletePurchaseOrderByID() throws JsonProcessingException {
  String requestUrl = String.format("%s/order/5/", baseURI);

  HttpEntity<String> request = new HttpEntity<>(headers);
  ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.DELETE, request, String.class);
  JsonNode JsonResponse = objectMapper.readTree(response.getBody());

  Assert.assertNotNull(response.getBody()); // Body null kontrolü

  Assert.assertEquals(200, response.getStatusCodeValue());
  Assert.assertEquals("OK", response.getStatusCode().name());

  Assert.assertNotNull(JsonResponse.path("code").asText());
  Assert.assertNotNull(JsonResponse.path("type").asText());
  Assert.assertNotNull(JsonResponse.path("message").asText());

  Assert.assertEquals(3, JsonResponse.size()); // Response Key Boyutu
  Assert.assertEquals(200,JsonResponse.path("code").asInt());
  Assert.assertEquals("unknown",JsonResponse.path("type").asText());
  Assert.assertEquals("5",JsonResponse.path("message").asText());

  Assert.assertEquals("[application/json]", response.getHeaders().getValuesAsList("Content-Type").toString()); // Content-Type içeriyor mu?
  Assert.assertEquals("[chunked]", response.getHeaders().getValuesAsList("Transfer-Encoding").toString()); // Transfer-Encoding içeriyor mu?
  Assert.assertEquals("[keep-alive]", response.getHeaders().getValuesAsList("Connection").toString()); // Connection içeriyor mu?
  Assert.assertEquals("[*]", response.getHeaders().getValuesAsList("Access-Control-Allow-Origin").toString()); // Access-Control-Allow-Origin içeriyor mu?
  Assert.assertEquals("[GET, POST, DELETE, PUT]", response.getHeaders().getValuesAsList("Access-Control-Allow-Methods").toString()); // Access-Control-Allow-Methods içeriyor mu?
  Assert.assertEquals("[Content-Type, api_key, Authorization]", response.getHeaders().getValuesAsList("Access-Control-Allow-Headers").toString()); // Access-Control-Allow-Headers içeriyor mu?
  Assert.assertEquals("[Jetty(9.2.9.v20150224)]", response.getHeaders().getValuesAsList("Server").toString()); // Server içeriyor mu?
}
```
</details>

## ✅ Returns pet inventories by status
https://petstore.swagger.io/v2/store/inventory

<details>
  <summary><h4>Testi görüntülemek için tıklayın</h4></summary>
  
```java
@Test
@Order(4)
public void ReturnsPetInventoriesByStatus() throws JsonProcessingException {
  String requestUrl = String.format("%s/inventory/", baseURI);

  HttpEntity<String> request = new HttpEntity<>(headers);
  ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
  String responseStr = restTemplate.getForObject(requestUrl, String.class);
  JsonNode JsonResponse = objectMapper.readTree(responseStr);

  Assert.assertNotNull(response.getBody()); // Body null kontrolü

  Assert.assertEquals(200, response.getStatusCodeValue());
  Assert.assertEquals("OK", response.getStatusCode().name());

  Assert.assertNotNull(JsonResponse);

  Assert.assertEquals("[application/json]", response.getHeaders().getValuesAsList("Content-Type").toString()); // Content-Type içeriyor mu?
  Assert.assertEquals("[chunked]", response.getHeaders().getValuesAsList("Transfer-Encoding").toString()); // Transfer-Encoding içeriyor mu?
  Assert.assertEquals("[keep-alive]", response.getHeaders().getValuesAsList("Connection").toString()); // Connection içeriyor mu?
  Assert.assertEquals("[*]", response.getHeaders().getValuesAsList("Access-Control-Allow-Origin").toString()); // Access-Control-Allow-Origin içeriyor mu?
  Assert.assertEquals("[GET, POST, DELETE, PUT]", response.getHeaders().getValuesAsList("Access-Control-Allow-Methods").toString()); // Access-Control-Allow-Methods içeriyor mu?
  Assert.assertEquals("[Content-Type, api_key, Authorization]", response.getHeaders().getValuesAsList("Access-Control-Allow-Headers").toString()); // Access-Control-Allow-Headers içeriyor mu?
  Assert.assertEquals("[Jetty(9.2.9.v20150224)]", response.getHeaders().getValuesAsList("Server").toString()); // Server içeriyor mu?
}
```
</details>
