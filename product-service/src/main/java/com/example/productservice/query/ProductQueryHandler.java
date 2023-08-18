package com.example.productservice.query;

import com.example.productservice.entity.Product;
import com.example.productservice.query.rest.ProductRestModel;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductQueryHandler {
    private final ProductRepository productRepository;

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductQuery query) {
        List<ProductRestModel> products = new ArrayList<>();
        List<Product> storedProducts = productRepository.findAll();
        for (Product product : storedProducts) {
            ProductRestModel productRestModel = new ProductRestModel();
            BeanUtils.copyProperties(product, productRestModel);
            products.add(productRestModel);
        }
        return products;
    }
}
