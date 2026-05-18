package com.learn.shein.mongo.resources;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learn.shein.mongo.config.PropertyHolder;
import com.learn.shein.mongo.model.CategoryConditions;
import com.learn.shein.mongo.model.SheinItem;
import com.learn.shein.mongo.service.CategoryConditionsService;
import com.learn.shein.mongo.service.SheinItemService;
import com.learn.util.Base64Utils;
import com.learn.util.JsonUtils;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
@RequestMapping("/spring/shein")
@RestController
@DependsOn({ "propertyHolder" })
public class MongoResource {

    @Transient
    public static final String Date_Time_Format = "yyyy-MM-dd_hh-mm-ss-";

    /**
     * Implicit constructor injection
     * `final` can **NOT** be used for property injection.
     */
    // @Autowired
    private final SheinItemService sheinItemService;

    /**
     * Implicit constructor injection
     * `final` can **NOT** be used for property injection.
     */
    // @Autowired
    private final CategoryConditionsService categoryConditionsService;

    /**
     * @Value should also be added into constructor argument. However, `lombok` does not know how to do it. Therefore, use property injection.
     * It is OK if you write your own constructor with @value in the argument.
     * Spring Boot automatically allow you to take environment variable:
     */
    // @Value("${image.url.prefix.ebay:}")
    // String imageUrlPrefix;

    private String[] electronics = {
            "electr",
            "phone",
            "samsung",
            "galaxy",
            "pro max",
            "unlocked",
            "verizon",
            "global",
            "att",
            "at&T",
            "tmobile",
            "cdma",
            "gsm",
            "dualband",
            "dual band",
            "motor",
    };

    private String findCategoryForTitle(String title) {
        if (title == null) {
            return null;
        }

        String tt = title.toLowerCase();

        AtomicBoolean found = new AtomicBoolean();
        Arrays.stream(electronics).forEach(electr -> {
            if (tt.contains(electr)) {
                found.set(true);
            }
        });
        if (found.get()) {
            return "electronics";
        }

        return "other";
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/getAllCategories"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getAllCategories", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<CategoryConditions>> getAllCategories()
        throws Exception {
        log.debug(() -> "Enter...");

        try {
            List<CategoryConditions> cats = this.categoryConditionsService.findAllCategories();

            return ResponseEntity.ok()
                    .body(cats); /** !!! Important !!! Response body cannot be entity. It must be a DTO. */
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/getConditionsByTitle/{title}"
     *
     * #### 2024-02-08 16:21:21 [INFO ] com.learn.exception.GlobalExceptionHandler(35) handleServletException()
     * [GET] https://52.3.85.231/spring/shein/getConditionsByTitle/Small%20USB%20Rechargeable%20LED%20Reading%20Book%20Light%20W/%20Flexible%20Clip%20Desk%20Table%20Lamp
     * #### 2024-02-08 16:21:21 [ERROR] com.learn.exception.GlobalExceptionHandler(36) handleServletException()
     * org.springframework.web.servlet.NoHandlerFoundException
     * org.springframework.web.servlet.NoHandlerFoundException: No endpoint GET /spring/shein/getConditionsByTitle/Small%20USB%20Rechargeable%20LED%20Reading%20Book%20Light%20W/%20Flexible%20Clip%20Desk%20Table%20Lamp.
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getConditionsByTitle/{title}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CategoryConditions> getConditionsByTitle(@PathVariable("title") String title)
        throws Exception {
        log.debug("Enter.... title: {}", () -> title);

        String newTitle = Base64Utils.decodeUrlsafeInput(title);

        log.debug("Enter.... new title: {}", () -> newTitle);

        String category = this.findCategoryForTitle(newTitle);

        try {
            return getConditionsByCategory(category);
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/getCategoryByTitle/{title}"
     *
     * #### 2024-02-08 16:21:21 [INFO ] com.learn.exception.GlobalExceptionHandler(35) handleServletException()
     * [GET] https://52.3.85.231/spring/shein/getConditionsByTitle/Small%20USB%20Rechargeable%20LED%20Reading%20Book%20Light%20W/%20Flexible%20Clip%20Desk%20Table%20Lamp
     * #### 2024-02-08 16:21:21 [ERROR] com.learn.exception.GlobalExceptionHandler(36) handleServletException()
     * org.springframework.web.servlet.NoHandlerFoundException
     * org.springframework.web.servlet.NoHandlerFoundException: No endpoint GET /spring/shein/getConditionsByTitle/Small%20USB%20Rechargeable%20LED%20Reading%20Book%20Light%20W/%20Flexible%20Clip%20Desk%20Table%20Lamp.
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getCategoryByTitle/{title}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> getCategoryByTitle(@PathVariable("title") String title)
        throws Exception {
        log.debug(() -> "Enter...");

        log.debug("Enter.... title: {}", () -> title);

        String newTitle = Base64Utils.decodeUrlsafeInput(title);

        log.debug("Enter.... new title: {}", () -> newTitle);

        try {
            var cat = findCategoryForTitle(newTitle);
            return ResponseEntity.ok()
                    .body(cat); /** !!! Important !!! Response body cannot be entity. It must be a DTO. */
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/getConditionsByCategory/{category}"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getConditionsByCategory/{category}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CategoryConditions> getConditionsByCategory(@PathVariable("category") String category)
        throws Exception {
        log.debug(() -> "Enter...");

        try {
            CategoryConditions categoryConditions = categoryConditionsService.findByCategory(category);

            return ResponseEntity.ok()
                    .body(categoryConditions); /** !!! Important !!! Response body cannot be entity. It must be a DTO. */
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/getAllItems"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getAllItems", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Iterable<SheinItem> getAllItems()
        throws Exception {
        log.debug(() -> "Enter...");

        try {
            Iterable<SheinItem> all = sheinItemService.getAll();

            return all;
            // throw new RestException("Test RestException");
            // throw new NullPointerException("Test NullPointerException");
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/getItem/{id}"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getItem/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<SheinItem> getItem(@PathVariable("id") Long id)
        throws Exception {
        log.debug(() -> "Enter...");

        try {
            Optional<SheinItem> opt = sheinItemService.findById(id);

            if (opt.isPresent()) {
                return ResponseEntity.ok()
                        .body(opt.get());
            } else {
                return ResponseEntity.ok()
                        .body(null);
            }

        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X GET "https://localhost:8443/spring/shein/getItemsForSeller/{sellerUsername}"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getItemsForSeller/{sellerUsername}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<SheinItem>> getItemsForSeller(@PathVariable("sellerUsername") String sellerUsername)
        throws Exception {
        log.debug(() -> "Enter...");

        log.debug("sellerUsername: {}", sellerUsername);

        sellerUsername = Base64Utils.decodeUrlsafeInput(sellerUsername);

        log.debug("sellerUsername: {}", sellerUsername);

        try {
            List<SheinItem> items = sheinItemService.findBySellerUsername(sellerUsername);

            return ResponseEntity.ok()
                    .body(items);

        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -X 'POST' \
            'https://localhost:8443/spring/shein/addItem' \
            -H 'Content-Type: multipart/form-data' \
            -F 'files=@java  cert.png;type=image/png' \
            -F 'files=@wp4029317-arizona-wallpapers.jpg;type=image/jpeg' \
            -F 'prodJson={"id": 0,  "name": "string",  "description": "string",  "imagePath": "string",  "price": 0}'
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/addItem", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<SheinItem> addItem(@RequestPart("files") List<MultipartFile> files, @RequestPart("itemJson") String itemJson)
        throws Exception {
        log.debug(() -> "Enter...");

        try {

            log.debug("prodJson: {}", () -> itemJson);

            final SheinItem item = JsonUtils.toObject(itemJson, SheinItem.class);

            log.debug("item: {}", item);

            String filePrefix = new SimpleDateFormat(Date_Time_Format).format(new Date());

            files.forEach(file -> {
                log.debug("multipart files: {}", () -> file.getOriginalFilename());

                String fileName = file.getOriginalFilename();
                if (fileName != null) {
                    fileName = filePrefix + fileName.replaceAll(" ", "_");

                    item.getImageFileNames().add(fileName);

                    try {
                        File targetFile = new File(PropertyHolder.Static_File_Location_OS, fileName);
                        Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        log.error("IOException", e);
                    }
                }
            });

            SheinItem model = this.sheinItemService.save(item);

            log.debug("model: {}", () -> model);

            return ResponseEntity.ok()
                    .body(model); /** !!! Important !!! Response body cannot be entity. It must be a DTO. */

            // throw new RestException("Test RestException");
            // throw new NullPointerException("Test NullPointerException");
        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X PUT "https://localhost:8443/spring/shein/listItem"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/listItem", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<SheinItem> listItem(@RequestBody IdBody idBody)
        throws Exception {
        log.debug("Enter... {}", () -> idBody);

        try {
            Optional<SheinItem> opt = updateItem(idBody.getId(), "list");

            if (opt.isPresent()) {
                return ResponseEntity.ok()
                        .body(opt.get());
            } else {
                return ResponseEntity.ok()
                        .body(null);
            }

        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X PUT "https://localhost:8443/spring/shein/unListItem"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/unListItem", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<SheinItem> unListItem(@RequestBody IdBody idBody)
        throws Exception {
        log.debug("Enter... {}", () -> idBody);

        try {
            Optional<SheinItem> opt = updateItem(idBody.getId(), "save");

            if (opt.isPresent()) {
                return ResponseEntity.ok()
                        .body(opt.get());
            } else {
                return ResponseEntity.ok()
                        .body(null);
            }

        } finally {
            log.info(() -> "Leave.");
        }
    }

    /**
     * curl -k -i -X DELETE "https://localhost:8443/spring/shein/deleteItem/{id}"
     */
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/deleteItem/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<SheinItem> deleteItem(@PathVariable("id") Long id)
        throws Exception {
        log.debug("Enter... {}", () -> id);

        try {
            Optional<SheinItem> opt = updateItem(id, "delete");

            if (opt.isPresent()) {
                return ResponseEntity.ok()
                        .body(opt.get());
            } else {
                return ResponseEntity.ok()
                        .body(null);
            }

        } finally {
            log.info(() -> "Leave.");
        }
    }

    private Optional<SheinItem> updateItem(Long id, String status) {
        return sheinItemService.updateStatusById(id, status);
    }

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class IdBody {

    private Long id;

}
