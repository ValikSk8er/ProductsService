package com.appsdeveloperblog.estore.ProductsService.command;

import com.appsdeveloperblog.estore.ProductsService.core.data.ProductLookupEntity;
import com.appsdeveloperblog.estore.ProductsService.core.data.ProductLookupRepository;
import com.appsdeveloperblog.estore.ProductsService.core.events.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    private final ProductLookupRepository productLookupRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());

        productLookupRepository.save(productLookupEntity);
    }

}
