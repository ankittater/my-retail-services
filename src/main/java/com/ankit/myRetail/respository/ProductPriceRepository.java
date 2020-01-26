package com.ankit.myRetail.respository;


import com.ankit.myRetail.model.ProductPrice;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 *  Interface to perform operation on Product Price Table
 */
public interface ProductPriceRepository {

    Optional<ProductPrice> get(@NotNull Long id);

    ProductPrice save(ProductPrice productPrice);

    int update(ProductPrice productPrice);

}
