package org.example.Controller;

import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ProductController {
    ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProductsEndpoint(){
        List<Product> products = productService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PostMapping("/product")
    public ResponseEntity<Object> postProductEndpoint(@RequestBody Product product) throws ProductException {
        productService.addProduct(product);
        try{
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("invalid product post", HttpStatus.BAD_REQUEST);
        }
    }
}
