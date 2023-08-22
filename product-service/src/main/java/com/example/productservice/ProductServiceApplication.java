package com.example.productservice;

import com.example.productservice.command.CreateProductCommandInterceptor;
import com.example.productservice.exception.ProductServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateProductInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer eventProcessingConfigurer) {
		eventProcessingConfigurer.registerListenerInvocationErrorHandler("product-group", conf -> new ProductServiceEventsErrorHandler());

		// if we do not create our  error handler class like ProductServiceEventsErrorHandler and can use PropagatingErrorHandler from axon
//		eventProcessingConfigurer.registerListenerInvocationErrorHandler("product-group", conf -> PropagatingErrorHandler.instance());
	}

}
