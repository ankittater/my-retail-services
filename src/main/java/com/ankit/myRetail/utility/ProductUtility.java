package com.ankit.myRetail.utility;

import com.ankit.myRetail.dto.CurrentPrice;
import com.ankit.myRetail.dto.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ankit.myRetail.model.ProductPrice;
import javax.inject.Singleton;
import java.util.LinkedHashMap;


@Singleton
public class ProductUtility {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     * @param product accept product details
     * @return convert product to product price and send back
     */
    public static ProductPrice convert(Product product) {
        CurrentPrice currentPrice = product.getCurrent_price();
        return new ProductPrice(product.getId(), currentPrice.getValue(), currentPrice.getCurrency_code());
    }

    public static Product getProduct(LinkedHashMap body) {
        JsonNode jsonNode = mapper.convertValue(body, JsonNode.class);
        String title=jsonNode.at("/product/item/product_description/title").asText();
        return new Product(title);

    }

    /**
     *
     * @param min lower range
     * @param max  upper range
     * @return  any random double value between min and max
     */
    public static double getRandomDoubleBetweenRange(double min, double max){
        return (int)(Math.random()*((max-min)+1))+min;

    }



}
