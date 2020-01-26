package com.ankit.myRetail.respository;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import com.ankit.myRetail.model.ProductPrice;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import javax.validation.constraints.NotNull;

@Singleton
public class ProductPriceRepositoryImpl implements ProductPriceRepository {

    @PersistenceContext
    protected final EntityManager entityManager;

    public ProductPriceRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     *
     * @param id product id
     * @return price details of product
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductPrice> get(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(ProductPrice.class, id));
    }

    /**
     *
     * @param productPrice instance consist of product price and code along with product id
     * @return details of latest price and code of product
     */
    @Override
    @Transactional
    public ProductPrice save(ProductPrice productPrice) {
        entityManager.persist(productPrice);
        return productPrice;
    }

    /**
     *
     * @param productPrice details of product price needs to be updated
     * @return no of rows updated
     */
    @Override
    @Transactional
    public int update(ProductPrice productPrice) {
        return entityManager.createQuery("UPDATE ProductPrice p SET value = :value , currency_code = :currency_code where id = :id")
                .setParameter("value", productPrice.getValue())
                .setParameter("currency_code", productPrice.getCurrency_code())
                .setParameter("id", productPrice.getId())
                .executeUpdate();
    }


}
