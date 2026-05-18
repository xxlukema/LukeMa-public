package com.learn.rest.resource.test.old_test_good;


import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.learn.exception.GlobalExceptionHandler;
import com.learn.rest.resource.FileUploadRestController;
import com.learn.service.FileUploadService;
import com.learn.utils.test.MockUtils;

import lombok.extern.log4j.Log4j2;


/**
 * This is for JUnit4:
 *    @RunWith(MockitoJUnitRunner.class)
 *    
 * This is for JUnit5:
 *     @ExtendWith(MockitoExtension.class)
 */
@Log4j2
@ExtendWith(MockitoExtension.class)
public class FileUploadRestControllerTest {

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private GlobalExceptionHandler globalExceptionHandler;

    /**
     * @InjectMocks will cause "components annotated with @Mock" be injected into "component that is annotated by @InjectMocks".
     */
    @InjectMocks
    @Spy
    private FileUploadRestController fileUploadRestController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp()
        throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(fileUploadRestController).setControllerAdvice(this.globalExceptionHandler).build();
    }

    @Test
    public void testSetUp()
        throws Exception {
        Assert.assertNotNull(this.fileUploadRestController);
    }

    @Test
    public void testUploadPdf()
        throws Exception {
        String url = "/spring/uploadPdf?title=Hello+world+520%20!";

        MockMultipartFile file = MockUtils.readFromMockMultipartFile("/invoice-sample.pdf");
        Assert.assertNotNull(file);

        // lenient().doNothing().when(this.fileUploadService).sayHello(Mockito.anyString());
        doNothing().when(this.fileUploadService).sayHello(Mockito.anyString());
        try {
            doNothing().doThrow(new Exception("test")).when(this.fileUploadRestController).sayHello(Mockito.anyString());
        } catch (Exception e) {
            log.error("test exception", e);
        }

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart(url).file(file).contentType(MediaType.APPLICATION_PDF);
        MvcResult result = this.mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk()).andReturn();

        Assert.assertNotNull(result);
        log.info("result: {}", result.getResponse().getContentAsString());
    }

}
