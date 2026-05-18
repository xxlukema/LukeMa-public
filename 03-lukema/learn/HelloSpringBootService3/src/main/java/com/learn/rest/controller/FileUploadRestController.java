package com.learn.rest.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.learn.exception.AppException;
import com.learn.service.FileUploadService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequestMapping("/spring")
@RestController
public class FileUploadRestController {

    private static final String STATUS_FORMAT = """
            {
              "status":"%s"
            }
            """;

    FileUploadService fileUploadService;

    /**
     * Implicit constructor injection:
     *
     * 1. Where there is only one constructor, the @Autowired for constructor injection can be ignored. That is called constructor injection.
     * 2. Constructor injection can avoid sporadic "NPE NullPointerException", that is caused by "invariant".
     */
    /**
    FileUploadRestController(FileUploadService fileuploadService) {
        this.fileUploadService = fileuploadService;
    }
    */

    @GetMapping("ping")
    public String ping() {

        log.debug(() -> "Called. ping()");

        return "FileUploadRestController OK!\n";
    }

    @GetMapping("ping2")
    public ResponseEntity<?> ping2() {

        String response = String.format(STATUS_FORMAT, "FileUploadRestController OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 1. Difference between @RequestParam and @RequestAttribute
     *
     *    @RequestParam is used to bind parameter values from 'query string' e.g. in http://www.example.com?myParam=3,
     *    myParam=3 can populate @RequestParam parameter.
     *
     *    On the other hand, @RequestAttribute is to access objects which have been populated on the server-side but during the
     *    same HTTP request, for example they can be populated in an interceptor or a filter.
     *
     *    requestId is set by BootWebFilter.
     *
     * 2. For file upload, the only correct way to get attached files is:
     *
     *    2.1 Do not use CommonsMultipartResolver as multipartResolver. And
     *    2.2 By using request.getMultiFileMap().
     *    2.3 On angular side:
     *        const formData: FormData = new FormData();
     *        this.files.forEach((item, i) => {
     *            formData.append('files[' + i + ']', item, item.name);
     *        });
     *
     *        this.httpClient.post<HttpEvent<any>>(this.uploadUrl, formData, {
     *            reportProgress: true,
     *            observe: 'events'
     *        })
     */
    @PostMapping(value = "file-upload")
    public ResponseEntity<String> postPing(MultipartHttpServletRequest request) {

        log.debug(() -> "Enter.");

        /**
         * 5. request.getMultiFileMap(). This is the only thing that works.
         *    This works with either one file or multiple files. And it is flexible with parameter name match:
         *    const formData: FormData = new FormData();
         *    formData.append('fileKey', file, file.name);
         */
        MultiValueMap<String, MultipartFile> multiMap = request.getMultiFileMap();
        log.debug("request.getMultiFileMap() size: {}", () -> multiMap.size());
        multiMap.forEach((key, value) -> {
            log.debug("request.getMultiFileMap MultiValueMap key: {}, value.size() {}", () -> key, () -> value.size());
            value.forEach(item -> log.debug("request.getMultiFileMap filename: {}, content: {}", () -> item.getName()));
        });
        String response = String.format(STATUS_FORMAT, "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Freddie Mac file uploader sample code
     *
     * @formatter:off
     */
    @PostMapping(value = "uploadPdf")
    public ResponseEntity<String> uploadPdf(@RequestBody(required = true) MultipartFile file,
                                            @Valid @Pattern(regexp = "[a-zA-Z0-9 -.]+") @NotEmpty @Size(min = 1, max = 100)
                                                @RequestParam(name = "title", required = false) String titile,
                                            MultipartHttpServletRequest request) throws Exception {
        // @formatter:on

        log.debug("body == null ? {}, request.getFile(\"file\"): {}", file == null, request.getFile("file") == null);

        if (file == null) {
            file = request.getFile("file");
        }

        if (file == null) {
            throw new AppException("Missing pdf file");
        }

        String type = new Tika().detect(file.getBytes());
        if (!MediaType.APPLICATION_PDF_VALUE.equals(type)) {
            throw new AppException("Only accept pdf file");
        }

        File fileToSave = new File("target/invoice-sample-uploaded.pdf");
        if (fileToSave.exists()) {
            try {
                java.nio.file.Files.delete(fileToSave.toPath());
                log.debug("Existing file deleted: true");
            } catch (IOException ex) {
                log.debug("Existing file deleted: false");
                throw new AppException("Cannot delete existing file: " + fileToSave.getAbsolutePath(), ex);
            }
        }

        try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
            fos.write(file.getBytes());
        }

        this.fileUploadService.sayHello("World");
        sayHello("World");

        String response = String.format("{\"status\":\"%s\"}", "OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void sayHello(String name)
        throws Exception {
        log.debug("Hello {} from FileUploadRestController", () -> name);
    }
}
