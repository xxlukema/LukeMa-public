package com.learn.eshop.repository;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;

import com.learn.eshop.dto.ProductDto;


class ProductRepositoryTest {

    @Test
    void givenPrePopulatedData_getProducts_ShouldReturnAProductList()
        throws InterruptedException {
        ProductCacheableRepository repository = new ProductCacheableRepository("Test");
        assertThat(repository.getProducts().size()).isEqualTo(3);
    }

    @Test
    void givenANewProductDto_addProduct_ShouldAddAndReturnDtoWithProdId()
        throws InterruptedException {
        ProductCacheableRepository repository = new ProductCacheableRepository("Test");
        assertThat(repository.getProducts().size()).isEqualTo(3);
        ProductDto productSamsung = ProductDto.builder().name("Sony 4K TV 75").price(3049.99).description("Sony LED 4k Smart TV").build();

        ProductDto createdDto = repository.addProduct(productSamsung);

        assertThat(repository.getProducts().size()).isEqualTo(4);
        assertThat(createdDto.getProductId()).isNotNull();
    }
}
