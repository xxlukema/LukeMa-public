package com.learn.rest.resource;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RequestMapping("/spring")
@RestController
public class FileDownloadController {

    protected String testFileName = "sample.pdf";

    // @GetMapping(value = "pdf/{fileName}", produces = "application/octet-stream")
    @GetMapping(value = "pdf/{fileName}", produces = "application/octet-stream")
    public ResponseEntity<?> getFileStream(@PathVariable("fileName") String fileName) {

        log.info("Called. {}", () -> fileName);

        if (!this.testFileName.equals(fileName)) {
            return new ResponseEntity<>("File not found: " + fileName, HttpStatus.NOT_FOUND);
        }

        /**
         * DO NOT use "this.getClass().getResourceAsStream(...)"!
         */
        try (InputStream is = FileDownloadController.class.getClassLoader().getResourceAsStream(fileName)) {

            byte[] bytes = is.readAllBytes();

            /**
             * This decoding in used in Census server. Is this for image download? Or is it because image data was pre-encoded? 
             */
            // byte[] decodedFileContent = Base64.getDecoder().decode(bytes);

            /**
             * (1) "application/octet-stream" can be set either in "producers" or use .contentType(MediaType.parseMediaType("application/octet-stream")).
             *     Without this header, frontend will save the file without opening a new tab ot pop-up.
             * (2) .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName) is to save file into a sample(1).pdf or sample-1.pdf.
             *     If this is not set, every click of frontend "download" button will over write old file in "Downloads" folder.  
             */
            // @formatter:off
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
					.body(new InputStreamResource(new ByteArrayInputStream(bytes)));
			        // .body(new InputStreamResource(new ByteArrayInputStream(decodedFileContent)));
			// @formatter:on

        } catch (Exception e) {
            log.error("Error decoding file data to Base64: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            /**
             * Returns Internal Server Error:
             */
            // return ResponseEntity.internalServerError().build();
            // return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

    }

}
