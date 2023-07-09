package sample.cafekiosk.spring.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
//@SpringBootTest // 문서를 생성하기 위해 스프링 서버를 띄워야 하므로 standalone 권장
public abstract class RestDocsSupport {

  protected MockMvc mockMvc;
  protected ObjectMapper objectMapper = new ObjectMapper();

  //  WebApplicationContext 직접 생성
//  @BeforeEach
//  void setUp(WebApplicationContext webApplicationContext, // SpringContext
//             RestDocumentationContextProvider provider) {
//    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//        .apply(documentationConfiguration(provider))
//        .build();
//  }

  //
  @BeforeEach
  void setUp(RestDocumentationContextProvider provider) {
    this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
        .apply(documentationConfiguration(provider))
        .build();
  }

  protected abstract Object initController();

}
