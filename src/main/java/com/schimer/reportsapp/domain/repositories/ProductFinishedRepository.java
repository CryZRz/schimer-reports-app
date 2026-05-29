package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.ProductFinishedEntity;

public class ProductFinishedRepository extends BaseRepository<ProductFinishedEntity> {
    public ProductFinishedRepository() {
        super(ProductFinishedEntity.class);
    }
}
