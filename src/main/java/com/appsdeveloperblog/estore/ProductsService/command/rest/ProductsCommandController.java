package com.appsdeveloperblog.estore.ProductsService.command.rest;

import com.appsdeveloperblog.estore.ProductsService.command.CreateProductCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsCommandController {

	private final CommandGateway commandGateway;

	@PostMapping
	public String createProduct(@Valid @RequestBody CreateProductRestModel productRestModel) {
		CreateProductCommand productCommand = CreateProductCommand.builder()
				.price(productRestModel.getPrice())
				.title(productRestModel.getTitle())
				.quantity(productRestModel.getQuantity())
				.productId(UUID.randomUUID().toString())
				.build();

		//exception handled by ProductsServiceErrorHandler
		String returnValue = commandGateway.sendAndWait(productCommand);

		return returnValue;
	}

}
