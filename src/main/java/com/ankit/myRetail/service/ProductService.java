package com.ankit.myRetail.service;

import com.ankit.myRetail.client.ProductPriceClient;
import com.ankit.myRetail.configuration.GlobalConstant;
import com.ankit.myRetail.dto.CurrentPrice;
import com.ankit.myRetail.dto.Product;
import com.ankit.myRetail.exception.ApiErrorBuilder;
import com.ankit.myRetail.exception.MyRetailException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import com.ankit.myRetail.client.MyRetailClient;
import com.ankit.myRetail.model.ProductPrice;
import com.ankit.myRetail.utility.ProductUtility;

import javax.inject.Singleton;
import java.util.LinkedHashMap;

@Singleton
public class ProductService {

    protected final MyRetailClient myRetailClient;
    protected final ProductPriceClient productPriceClient;

    public ProductService(MyRetailClient myRetailClient, ProductPriceClient productPriceClient, ProductUtility productUtility) {
        this.myRetailClient = myRetailClient;
        this.productPriceClient = productPriceClient;
    }

    /**
     *
     * @param id
     * @return product instance by fetching title from myRetailClient and prices from priceClient
     */
    public Product getProductDetails(long id) {
        HttpResponse productResponse = myRetailClient.fetchProduct(id);
        if (productResponse.getStatus() == HttpStatus.OK) {
            if (productResponse.getBody().isPresent()) {
                try {
                    Product product = ProductUtility.getProduct((LinkedHashMap) productResponse.getBody().get());
                    product.setId(id);
                    ProductPrice productPrice = productPriceClient.fetchProductPrice(product.getId());
                    product.setCurrent_price(new CurrentPrice(productPrice.getValue(), productPrice.getCurrency_code()));
                    return product;
                } catch (Exception e) {
                    throw new MyRetailException(new ApiErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, GlobalConstant.INTERNAL_SERVER_ERROR_MESSAGE).setDebugMessage(e.getMessage()));
                }
            } else {
                throw new MyRetailException(new ApiErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, GlobalConstant.INTERNAL_SERVER_ERROR_MESSAGE).setDebugMessage("No data received from client"));
            }
        } else if (productResponse.getStatus() == HttpStatus.NOT_FOUND) {
            throw new MyRetailException(new ApiErrorBuilder(HttpStatus.NOT_FOUND, "No product found with this id"));
        } else {
            throw new MyRetailException(new ApiErrorBuilder(productResponse.getStatus(), null));
        }
    }

}
