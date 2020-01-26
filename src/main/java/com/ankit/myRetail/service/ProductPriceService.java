package com.ankit.myRetail.service;

import com.ankit.myRetail.dto.Product;
import com.ankit.myRetail.exception.ApiErrorBuilder;
import com.ankit.myRetail.exception.MyRetailException;
import com.ankit.myRetail.respository.ProductPriceRepository;
import io.micronaut.http.HttpStatus;
import com.ankit.myRetail.model.ProductPrice;
import com.ankit.myRetail.utility.ProductUtility;
import javax.inject.Singleton;
import java.util.Optional;


@Singleton
public class ProductPriceService {

    protected final ProductPriceRepository productPriceRepository;

    public ProductPriceService(ProductPriceRepository productPriceRepository, ProductUtility productUtility) {
        this.productPriceRepository = productPriceRepository;
    }

    /**
     * @param id accept product id
     * @return instance of product price table
     */
    public Optional<ProductPrice> get(Long id) {
        return productPriceRepository.get(id);
    }

    /**
     *
     * @param product take product that needs to be saved
     * @return return same product if saved successfully
     */
    public ProductPrice save(Product product) {
        return productPriceRepository.save(ProductUtility.convert(product));
    }

    /**
     *
     * @param product
     * @return no of rows updated
     */
    public int update(Product product) {
        Optional<ProductPrice> productPrice = get(product.getId());
        if (!productPrice.isPresent()) {
            throw new MyRetailException(new ApiErrorBuilder(HttpStatus.BAD_REQUEST, "Product not found requested given id"));
        }
        ProductPrice productPriceInstance = productPrice.get();
        productPriceInstance.setCurrency_code(product.getCurrent_price().getCurrency_code());
        productPriceInstance.setValue(product.getCurrent_price().getValue());
        return productPriceRepository.update(productPriceInstance);
    }
}
