package com.empresa.crudjpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.crudjpa.entities.Product;
import com.empresa.crudjpa.repositories.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository repositoryProduct;

    @Transactional(readOnly = true)    
    @Override
    public List<Product> findAll() {
        return (List<Product>) repositoryProduct.findAll();
    }
    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return repositoryProduct.findById(id);
    }
    @SuppressWarnings("null")
    @Transactional
    @Override
    public Product save(Product product) {
        return repositoryProduct.save(product);
    }
    
    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        @SuppressWarnings("null")
        Optional<Product> productOptional = repositoryProduct.findById(id);
        if(productOptional.isPresent()){
            Product productDb = productOptional.orElseThrow();
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());;
            return Optional.of(repositoryProduct.save(productDb));
        }
        return productOptional;
    }
    @SuppressWarnings("null")
    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptional = repositoryProduct.findById(id);
        productOptional.ifPresent(productDb->{
            repositoryProduct.delete(productDb);
        });
        return productOptional;
    }
}
