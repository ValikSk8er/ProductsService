package com.appsdeveloperblog.estore.ProductsService.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private final String productId;
    private final String title;
    private final Integer quantity;
    private final BigDecimal price;
}
