package com.ankit.myRetail.service;

import com.ankit.myRetail.client.ProductPriceClient;
import com.ankit.myRetail.dto.CurrentPrice;
import com.ankit.myRetail.dto.Product;
import com.ankit.myRetail.exception.MyRetailException;
import com.ankit.myRetail.model.ProductPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import com.ankit.myRetail.client.MyRetailClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import javax.inject.Inject;
import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MicronautTest
class ProductServiceTest {

    @Inject
    ProductService productService;

    @Inject
    MyRetailClient myRetailClient;

    @Inject
    ProductPriceClient productPriceClient;


    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void getProductDetails(long id) {
        ProductPrice productPrice = getProductPrice(id);
        Product product = getProduct(id);
        String title = "The Big Lebowski (Blu-ray)";
        product.setName(title);
        when(productPriceClient.fetchProductPrice(id)).thenReturn(productPrice);
        when(myRetailClient.fetchProduct(id)).
                thenReturn(HttpResponse.status(HttpStatus.OK).
                        body(getProductDetails()));
        Product productInstance = productService.getProductDetails(id);
        assertEquals(productPrice.getCurrency_code(), productInstance.getCurrent_price().getCurrency_code());
        assertEquals(title, productInstance.getName());
        verify(myRetailClient).fetchProduct(id);
    }

    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void verifyProductDetailsWithNull(long id) {
        Product product = getProduct(id);
        String title = "The Big Lebowski (Blu-ray)";
        product.setName(title);
        when(myRetailClient.fetchProduct(id)).
                thenReturn(HttpResponse.status(HttpStatus.OK).body(getProductDetails())).
                thenReturn(HttpResponse.status(HttpStatus.OK)).
                thenReturn(HttpResponse.status(HttpStatus.OK).body(new LinkedHashMap<>())).
                thenReturn(HttpResponse.status(HttpStatus.NOT_FOUND)).
                thenReturn(HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR));

        int testCount = 5;
        for (int i = 0; i < testCount; i++) {
            Assertions.assertThrows(MyRetailException.class, () -> productService.getProductDetails(id));
        }
        verify(myRetailClient, times(testCount)).fetchProduct(id);

    }

    @MockBean(MyRetailClient.class)
    MyRetailClient myRetailClient() {
        return Mockito.mock(MyRetailClient.class);
    }

    @MockBean(ProductPriceClient.class)
    ProductPriceClient productPriceClient() {
        return Mockito.mock(ProductPriceClient.class);
    }

    private Product getProduct(long id) {
        return new Product(id
                , "test-product", new CurrentPrice(10.0, "USD"));
    }

    private LinkedHashMap getProductDetails() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String responseData = "{\"product\":{\"available_to_promise_network\":{\"product_id\":\"13860428\",\"id_type\":\"TCIN\",\"available_to_promise_quantity\":90.0,\"street_date\":\"2011-11-15T06:00:00.000Z\",\"availability\":\"AVAILABLE\",\"online_available_to_promise_quantity\":90.0,\"stores_available_to_promise_quantity\":0.0,\"availability_status\":\"IN_STOCK\",\"multichannel_options\":[],\"is_infinite_inventory\":false,\"loyalty_availability_status\":\"IN_STOCK\",\"loyalty_purchase_start_date_time\":\"1970-01-01T00:00:00.000Z\",\"is_loyalty_purchase_enabled\":false,\"is_out_of_stock_in_all_store_locations\":false,\"is_out_of_stock_in_all_online_locations\":false},\"item\":{\"tcin\":\"13860428\",\"bundle_components\":{},\"dpci\":\"058-34-0436\",\"upc\":\"025192110306\",\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"downstream_description\":\"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\",\"bullet_description\":[\"<B>Movie Studio:</B> Universal Studios\",\"<B>Movie Genre:</B> Comedy\",\"<B>Run Time (minutes):</B> 119\",\"<B>Software Format:</B> Blu-ray\"]},\"buy_url\":\"https://www.target.com/p/the-big-lebowski-blu-ray/-/A-13860428\",\"enrichment\":{\"images\":[{\"base_url\":\"https://target.scene7.com/is/image/Target/\",\"primary\":\"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\",\"content_labels\":[{\"image_url\":\"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\"}]}],\"sales_classification_nodes\":[{\"node_id\":\"1s0rs\"},{\"node_id\":\"hp0vg\"},{\"node_id\":\"5xswx\"},{\"node_id\":\"g7ito\"}]},\"return_method\":\"This item can be returned to any Target store or Target.com.\",\"handling\":{},\"recall_compliance\":{\"is_product_recalled\":false},\"tax_category\":{\"tax_class\":\"G\",\"tax_code_id\":99999,\"tax_code\":\"99999\"},\"display_option\":{\"is_size_chart\":false},\"fulfillment\":{\"is_po_box_prohibited\":true,\"po_box_prohibited_message\":\"We regret that this item cannot be shipped to PO Boxes.\",\"box_percent_filled_by_volume\":0.27,\"box_percent_filled_by_weight\":0.43,\"box_percent_filled_display\":0.43},\"package_dimensions\":{\"weight\":\"0.18\",\"weight_unit_of_measure\":\"POUND\",\"width\":\"5.33\",\"depth\":\"6.65\",\"height\":\"0.46\",\"dimension_unit_of_measure\":\"INCH\"},\"environmental_segmentation\":{\"is_hazardous_material\":false,\"has_lead_disclosure\":false},\"manufacturer\":{},\"product_vendors\":[{\"id\":\"1984811\",\"manufacturer_style\":\"025192110306\",\"vendor_name\":\"Ingram Entertainment\"},{\"id\":\"4667999\",\"manufacturer_style\":\"61119422\",\"vendor_name\":\"UNIVERSAL HOME VIDEO\"},{\"id\":\"1979650\",\"manufacturer_style\":\"61119422\",\"vendor_name\":\"Universal Home Ent PFS\"}],\"product_classification\":{\"product_type\":\"542\",\"product_type_name\":\"ELECTRONICS\",\"item_type_name\":\"Movies\",\"item_type\":{\"category_type\":\"Item Type: MMBV\",\"type\":300752,\"name\":\"movies\"}},\"product_brand\":{\"brand\":\"Universal Home Video\",\"manufacturer_brand\":\"Universal Home Video\",\"facet_id\":\"55zki\"},\"item_state\":\"READY_FOR_LAUNCH\",\"specifications\":[],\"attributes\":{\"gift_wrapable\":\"N\",\"has_prop65\":\"N\",\"is_hazmat\":\"N\",\"manufacturing_brand\":\"Universal Home Video\",\"max_order_qty\":10,\"street_date\":\"2011-11-15\",\"media_format\":\"Blu-ray\",\"merch_class\":\"MOVIES\",\"merch_classid\":58,\"merch_subclass\":34,\"return_method\":\"This item can be returned to any Target store or Target.com.\",\"ship_to_restriction\":\"United States Minor Outlying Islands,American Samoa (see also separate entry under AS),Puerto Rico (see also separate entry under PR),Northern Mariana Islands,Virgin Islands, U.S.,APO/FPO,Guam (see also separate entry under GU)\"},\"country_of_origin\":\"US\",\"relationship_type_code\":\"Stand Alone\",\"subscription_eligible\":false,\"ribbons\":[],\"tags\":[],\"ship_to_restriction\":\"This item cannot be shipped to the following locations: United States Minor Outlying Islands, American Samoa, Puerto Rico, Northern Mariana Islands, Virgin Islands, U.S., APO/FPO, Guam\",\"estore_item_status_code\":\"A\",\"is_proposition_65\":false,\"return_policies\":{\"user\":\"Regular Guest\",\"policyDays\":\"30\",\"guestMessage\":\"This item must be returned within 30 days of the ship date. See return policy for details.\"},\"gifting_enabled\":false,\"packaging\":{\"is_retail_ticketed\":false}},\"circle_offers\":{\"universal_offer_exists\":false,\"non_universal_offer_exists\":true}}}";

            return mapper.readValue(responseData, LinkedHashMap.class);
        } catch (Exception e) {
            return new LinkedHashMap();
        }

    }

    private ProductPrice getProductPrice(long id) {
        return new ProductPrice(id, id * 10.0, "USD");
    }


}