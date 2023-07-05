package my.interview;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Slf4j
public class RestControllerAdviceTests {
  @Autowired private MockMvc mockMvc;

  @Test
  public void testException_FizzException() throws Exception {
    mockMvc
        .perform(get("/controller_advice/fizz"))
        .andExpect(jsonPath("$.message").value("Fizz Exception has been thrown"))
        .andExpect(jsonPath("$.errorReason").value("Internal Server Error"))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void testException_BuzzException() throws Exception {
    mockMvc
        .perform(get("/controller_advice/buzz"))
        .andExpect(jsonPath("$.message").value("Buzz Exception has been thrown"))
        .andExpect(jsonPath("$.errorReason").value("Bad Request"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testException_FizzBuzzException() throws Exception {
    mockMvc
        .perform(get("/controller_advice/fizzbuzz"))
        .andExpect(jsonPath("$.message").value("FizzBuzz Exception has been thrown"))
        .andExpect(jsonPath("$.errorReason").value("Insufficient Storage"))
        .andExpect(status().isInsufficientStorage());
  }

  @Test
  public void testException_Success() throws Exception {
    mockMvc
        .perform(get("/controller_advice/success"))
        .andExpect(jsonPath("$.message").value("Successfully completed fizzbuzz test"))
        .andExpect(jsonPath("$.statusCode").value("200"))
        .andExpect(status().isOk());
  }

  @Test
  public void testHealth_FormatFull() throws Exception {
    mockMvc
        .perform(get("/health?format=full"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("full"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.dateTime").value("31/12/2019"))
        .andDo(
            mvcResult ->
                log.info(
                    "TEST - url: {}, response: {}",
                    "/health?format=full",
                    mvcResult.getResponse().getContentAsString()));
  }

  @Test
  public void testHealth_FormatShort() throws Exception {
    mockMvc
        .perform(get("/health?format=short"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("short"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.dateTime").doesNotHaveJsonPath())
        .andDo(
            mvcResult ->
                log.info(
                    "TEST - url: {}, response: {}",
                    "/health?format=short",
                    mvcResult.getResponse().getContentAsString()));
  }
}
