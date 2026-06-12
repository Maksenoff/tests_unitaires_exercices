package org.example.Service;

import org.example.Model.Product;
import org.example.Repositoriy.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findByName (String name){
        return productRepository.findByName(name);
    }

    public List<Product> findByPrice (float price){
        return productRepository.findByPrice(price);
    }

    public List<Product> findByCategory (String category){
        return productRepository.findByCategory(category);
    }


}
