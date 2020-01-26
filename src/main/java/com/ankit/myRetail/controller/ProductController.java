package com.ankit.myRetail.controller;


import com.ankit.myRetail.dto.Product;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import com.ankit.myRetail.model.ProductPrice;
import com.ankit.myRetail.service.ProductPriceService;
import com.ankit.myRetail.service.ProductService;
import com.ankit.myRetail.utility.ProductUtility;

import javax.validation.Valid;

/**
 * Expose products related endpoints
 */
@Controller("/products")
public class ProductController {

    protected final ProductService productService;
    protected final ProductPriceService productPriceService;


    public ProductController(ProductService productService, ProductPriceService productPriceService) {
        this.productService = productService;
        this.productPriceService = productPriceService;
    }


    /**
     * @param id product id which needs to be fetched
     * @return return Product instance based on id
     */
    @Get("/{id}")
    public Product getProduct(Long id) {
        return productService.getProductDetails(id);
    }


    /**
     * @param product accept product that needs to be saved into database
     * @return return HttpResponse with ProductPrice details
     */
    @Post
    public HttpResponse<ProductPrice> save(@Body @Valid Product product) {
        ProductPrice productPrice = productPriceService.save(product);
        return HttpResponse
                .created(productPrice);
    }

    /**
     * @param product Product instance to update price
     * @return on successful update HtttpResponse with status NO_CONTENT,On Error return different HttpStatus
     */
    @Put
    public HttpResponse update(@Body @Valid Product product) {
        productPriceService.update(product);
        return HttpResponse.noContent();
    }

    /**
     * @param id Id of the Product whose price has to be fetched
     * @return return ProductPrice instance from db
     */
    @Get("/{id}/price")
    public ProductPrice getPrice(Long id) {
        return productPriceService.get(id).orElse(new ProductPrice(id, ProductUtility.getRandomDoubleBetweenRange(5, 10), "USD"));
    }


}
