package com.appsdeveloperblog.estore.ProductsService.query;

import com.appsdeveloperblog.estore.ProductsService.core.data.ProductEntity;
import com.appsdeveloperblog.estore.ProductsService.core.data.ProductsRepository;
import com.appsdeveloperblog.estore.ProductsService.core.events.ProductCreatedEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private final ProductsRepository productsRepository;

    @ExceptionHandler(resultType=Exception.class)
    public void handle(Exception exception) throws Exception {
        //will be handled by ProductsServiceEventsErrorHandler and thrown again
        throw exception;
    }

    @ExceptionHandler(resultType=IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        // Log error message
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);

        productsRepository.save(productEntity);
    }

    @EventHandler
    public void on(ProductReservedEvent event) {
        ProductEntity productEntity = productsRepository.findByProductId(event.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - event.getQuantity());
        productsRepository.save(productEntity);

        log.info("ProductReservedEvent is called for productId: " + event.getProductId());
    }

}
