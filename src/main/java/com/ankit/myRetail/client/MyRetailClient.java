package com.ankit.myRetail.client;

import com.ankit.myRetail.configuration.MyRetailClientConfiguration;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;


@Client(
        value = "${myRetail.endpoint}",
        configuration = MyRetailClientConfiguration.class)
public interface MyRetailClient {

    @Get("/${myRetail.version}/pdp/tcin/{id}?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics")
    HttpResponse fetchProduct(long id);
}
