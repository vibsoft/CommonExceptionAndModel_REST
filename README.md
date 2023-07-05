
REST API to emulate common exception handling with RestControllerAdvice and JSON model mapping with JSON annotations

Exceptions are to be thrown based on the value of the path param `{code}` passed to the rest API.

Here is a series of requests and their corresponding expected responses:

`GET /controller_advice/fizz`:

Response Code: 500

Response Body:
```json
{
    "message": "Fizz Exception has been thrown",
    "errorReason" : "Internal Server Error"
}
```

`GET /controller_advice/buzz`:

Response Code: 400

Response Body:
```json
{
    "message": "Buzz Exception has been thrown",
    "errorReason" : "Bad Request"
}
```

`GET /controller_advice/fizzbuzz`:

Response Code: 507

Response Body:
```json
{
    "message": "FizzBuzz Exception has been thrown",
    "errorReason" : "Insufficient Storage"
}
```

`GET /controller_advice/success`:

Response Code: 200

Response Body:
```json
{
    "message": "Successfully completed fizzbuzz test",
    "statusCode": "200"
}
```

`GET /health?format=short`:

Response Code: 200

Response Body:
```json
{
  "status": "OK",
  "message": "short"
}
```

`GET /health?format=full`:

Response Code: 200

Response Body:
```json
 {
  "status": "OK",
  "message": "full",
  "dateTime": "31/12/2019"
}
```


How to run tests:

From command line, will run test to have predefined output in logs - mvn clean install
From IDE (IDEA) - run /test/RestControllerTests.testExchangeMachine_Scenario_1() test
from Postman - run APIs in Postman

```
public class RestControllerAdviceTests {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testException_FizzException() throws Exception {
    mockMvc.perform(get("/controller_advice/fizz"))
            .andExpect(jsonPath("$.message").value("Fizz Exception has been thrown"))
            .andExpect(jsonPath("$.errorReason").value("Internal Server Error"))
            .andExpect(status().isInternalServerError());
  }

  @Test
  public void testException_BuzzException() throws Exception {
    mockMvc.perform(get("/controller_advice/buzz"))
            .andExpect(jsonPath("$.message").value("Buzz Exception has been thrown"))
            .andExpect(jsonPath("$.errorReason").value("Bad Request"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void testException_FizzBuzzException() throws Exception {
    mockMvc.perform(get("/controller_advice/fizzbuzz"))
            .andExpect(jsonPath("$.message").value("FizzBuzz Exception has been thrown"))
            .andExpect(jsonPath("$.errorReason").value("Insufficient Storage"))
            .andExpect(status().isInsufficientStorage());
  }

  @Test
  public void testException_Success() throws Exception {
    mockMvc.perform(get("/controller_advice/success"))
            .andExpect(jsonPath("$.message").value("Successfully completed fizzbuzz test"))
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(status().isOk());
  }

  @Test
  public void testHealth_FormatFull() throws Exception {
    mockMvc.perform(get("/health?format=full"))
            .andExpect(jsonPath("$.message").value("full"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.dateTime").value("31/12/2019"))
            .andExpect(status().isOk());
  }

  @Test
  public void testHealth_FormatShort() throws Exception {
    mockMvc.perform(get("/health?format=short"))
            .andExpect(jsonPath("$.message").value("short"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.dateTime").doesNotHaveJsonPath())
            .andExpect(status().isOk());
  }
}
