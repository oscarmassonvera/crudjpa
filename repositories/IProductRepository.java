package com.empresa.crudjpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.empresa.crudjpa.entities.Product;

public interface IProductRepository extends CrudRepository<Product,Long> {

}
