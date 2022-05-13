package com.appsdeveloperblog.estore.ProductsService;

import com.appsdeveloperblog.estore.ProductsService.command.interceptors.CreateProductCommandInterceptor;
import com.appsdeveloperblog.estore.ProductsService.core.errorhandling.ProductsServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		CreateProductCommandInterceptor interceptor = context.getBean(CreateProductCommandInterceptor.class);
		commandBus.registerDispatchInterceptor(interceptor);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("product-group",
				conf -> new ProductsServiceEventsErrorHandler());

//		configurer.registerListenerInvocationErrorHandler("product-group",
//				conf -> PropagatingErrorHandler.instance());
	}

}
