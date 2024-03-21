package com.empresa.crudjpa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.crudjpa.entities.Product;
import com.empresa.crudjpa.services.IProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService serviceProduct;

    @GetMapping
    public List<Product> list(){
        return serviceProduct.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){
        Optional<Product> producOptional = serviceProduct.findById(id);
        if (producOptional.isPresent()) {
            return ResponseEntity.ok(producOptional.orElseThrow());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) { 
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceProduct.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product,BindingResult result, @PathVariable Long id ) {
            if (result.hasFieldErrors()) {
                return validation(result);
            }
        Optional<Product> prOptional = serviceProduct.update(id,product);
        if(prOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(prOptional.orElseThrow());
        }
        else{
            return ResponseEntity.notFound().build();            
        } 
    }
    
    // CONTROL K+C COMENTAR 
    // CONTROL K+U DESCOMENTAR

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        // Product product = new Product();
        // product.setId(id);
        Optional<Product> producOptional = serviceProduct.delete(id);
        if (producOptional.isPresent()) {
            return ResponseEntity.ok(producOptional.orElseThrow());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(),"El campo "+ err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
