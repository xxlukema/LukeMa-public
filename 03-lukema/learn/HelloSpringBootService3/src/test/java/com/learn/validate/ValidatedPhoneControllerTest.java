package com.learn.validate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


class ValidatedPhoneControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ValidatedPhoneController())
                .build();
    }

    @Test
    void givenPhonePageUri_whenMockMvc_thenReturnsPhonePage()
        throws Exception {
        this.mockMvc.perform(get("/validatePhone"))
                .andExpect(view().name("phoneHome"));
    }

    @Test
    void givenPhoneURIWithPostAndFormData_whenMockMVC_thenVerifyErrorResponse()
        throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/addValidatePhone")
                .accept(MediaType.TEXT_HTML)
                .param("phone", "123"))
                .andExpect(model().attributeHasFieldErrorCode("validatedPhone", "phone", "ContactNumberConstraint"))
                .andExpect(view().name("error/400"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void givenPhoneURIWithPostAndFormData_whenMockMVC_thenVerifySuccessResponse()
        throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/addValidatePhone")
                .accept(MediaType.TEXT_HTML)
                .param("phone", "1234567890"))
                .andExpect(model().attributeHasNoErrors("validatedPhone"))
                .andExpect(view().name("phoneHome"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
