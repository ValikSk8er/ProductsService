package com.appsdeveloperblog.estore.ProductsService.query;

import com.appsdeveloperblog.estore.ProductsService.core.data.ProductEntity;
import com.appsdeveloperblog.estore.ProductsService.core.data.ProductsRepository;
import com.appsdeveloperblog.estore.ProductsService.query.rest.ProductRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductsQueryHandler {

    private final ProductsRepository productsRepository;

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery query) {
        List<ProductRestModel> productRestModels = new ArrayList<>();

        List<ProductEntity> storedProducts = productsRepository.findAll();
        for (ProductEntity entity : storedProducts) {
            ProductRestModel productRestModel = new ProductRestModel();
            BeanUtils.copyProperties(entity, productRestModel);
            productRestModels.add(productRestModel);
        }

        return productRestModels;
    }

}
