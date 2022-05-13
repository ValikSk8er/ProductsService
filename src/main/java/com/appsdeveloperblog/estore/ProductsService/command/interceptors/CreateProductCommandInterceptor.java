package com.appsdeveloperblog.estore.ProductsService.command.interceptors;

import com.appsdeveloperblog.estore.ProductsService.command.CreateProductCommand;
import com.appsdeveloperblog.estore.ProductsService.core.data.ProductLookupEntity;
import com.appsdeveloperblog.estore.ProductsService.core.data.ProductLookupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

//interceptor should be registered
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRepository productLookupRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> list) {
        return ((index, command) -> {
            log.info("Intercepted command: " + command.getPayloadType());

            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();

                validateCreateProductCommand(createProductCommand);

                checkIfCommandStoredBefore(createProductCommand);
            }

            return command;
        });
    }

    private void validateCreateProductCommand(CreateProductCommand createProductCommand) {
        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be less or equal than zero");
        }
        if (createProductCommand.getTitle() == null ||
                createProductCommand.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }

    private void checkIfCommandStoredBefore(CreateProductCommand createProductCommand) {
        ProductLookupEntity productLookupEntity = productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(),
                createProductCommand.getTitle());

        if (productLookupEntity != null) {
            throw new IllegalStateException(
                    String.format("Product with productId %s or title %s already exist",
                            createProductCommand.getProductId(), createProductCommand.getTitle())
            );
        }
    }
}
