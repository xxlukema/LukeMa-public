package com.learn.shein.psql.resources;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learn.exception.AppException;
import com.learn.shein.mongo.config.PropertyHolder;
import com.learn.shein.psql.entity.Country;
import com.learn.shein.psql.entity.SheinProduct;
import com.learn.shein.psql.entity.SheinProductImage;
import com.learn.shein.psql.service.SheinDataService;
import com.learn.util.JsonUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@RequiredArgsConstructor
@Log4j2
@RequestMapping("/spring/shein")
@RestController
public class PsqlResource {

    /**
     * Spring Boot automatically allow you to take environment variable:
     */
    @Value("${HOSTNAME:localhost}")
    String hostname;

    @Value("${image.url.prefix:}")
    String imageUrlPrefix;

    /**
     * Implicit constructor injection
     */
    // Auto generated constructor by lombok
    @Autowired
    private final SheinDataService sheinDataService;

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/ping"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/ping", produces = { MediaType.APPLICATION_JSON_VALUE })
    public SheinProduct ping()
        throws Exception {
        log.debug(() -> "Enter...");

        try {
            SheinProduct product = new SheinProduct();
            return product;
            // throw new RestException("Test RestException");
            // throw new NullPointerException("Test NullPointerException");
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/allCountries"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/allCountries", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Iterable<Country> allCountries()
        throws Exception {
        log.debug(() -> "Enter...");

        log.debug("hostname: {}", () -> hostname);

        try {
            Iterable<Country> all = sheinDataService.findAllCountries();

            return all;
            // throw new RestException("Test RestException");
            // throw new NullPointerException("Test NullPointerException");
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/listProducts"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/listProducts", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Iterable<SheinProduct> listProducts()
        throws Exception {
        log.debug(() -> "Enter...");

        log.debug("hostname: {}", () -> hostname);

        try {
            Iterable<SheinProduct> all = sheinDataService.findAllSheinProducts();

            return all;
            // throw new RestException("Test RestException");
            // throw new NullPointerException("Test NullPointerException");
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * !!! It returns null if the `id` is not found.
     * curl -k -i -X GET "https://localhost:8443/spring/shein/getProduct/3"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getProduct/{productId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public SheinProduct getProduct(@PathVariable("productId") Long productId)
        throws Exception {
        log.debug(() -> "Enter...");

        try {
            Optional<SheinProduct> opt = sheinDataService.findSheinProductById(productId);

            if (opt.isPresent()) {
                return opt.get();
            } else {
                return null;
            }

            // throw new RestException("Test RestException");
            // throw new NullPointerException("Test NullPointerException");
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -X 'POST' \
            'https://localhost:8443/spring/shein/addProduct' \
            -H 'Content-Type: multipart/form-data' \
            -F 'files=@java  cert.png;type=image/png' \
            -F 'files=@wp4029317-arizona-wallpapers.jpg;type=image/jpeg' \
            -F 'prodJson={"id": 0,  "name": "string",  "description": "string",  "imagePath": "string",  "price": 0}'
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/addProduct", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Iterable<SheinProduct> addProduct(@RequestPart("files") List<MultipartFile> files, @RequestPart("prodJson") String prodJson)
        throws Exception {
        log.debug(() -> "Enter...");

        try {

            log.debug("prodJson: {}", () -> prodJson);

            SheinProduct product = JsonUtils.toObject(prodJson, SheinProduct.class);

            log.debug("product: {}", () -> product);

            product.setImageUrlPrefix(imageUrlPrefix);
            SheinProduct curProduct = sheinDataService.save(product);
            product.setId(curProduct.getId());

            log.debug("product: {}", () -> product);

            files.forEach(file -> {
                log.debug("multipart files: {}", () -> file.getOriginalFilename());

                String fileName = file.getOriginalFilename();
                if (fileName != null) {
                    fileName = fileName.replaceAll(" ", "_");

                    try {
                        File targetFile = new File(PropertyHolder.Static_File_Location_OS, fileName);
                        Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        log.error("IOException", e);
                    }
                }

                SheinProductImage productImage = new SheinProductImage();
                productImage.setFileName(fileName);
                productImage.setProduct(product);
                try {
                    sheinDataService.save(productImage);
                } catch (AppException e) {
                    log.error("sheinDataService.save(productImage) Exception", e);
                }
            });

            Iterable<SheinProduct> all = sheinDataService.findAllSheinProducts();

            return all;
            // throw new RestException("Test RestException");
            // throw new NullPointerException("Test NullPointerException");
        } finally {
            log.info(() -> "Leave.");
        }
    }
}
