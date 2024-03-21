package org.example.Controller;

import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
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
    @PostMapping("seller/{id}/product")
    public ResponseEntity<Product> postProductEndpoint(@RequestBody Product product, @PathVariable long id){
        try{
            productService.addProduct(id, product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch(ProductException e){
            return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductByIdEndpoint(@PathVariable long id){
        try{
            Product product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (ProductException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProductEndpoint(@PathVariable long id){
        try{
            if(productService.getProductById(id) != null) {
                productService.deleteProduct(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (ProductException e){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return null;
    }
    @PutMapping("/seller/{id}/product/{productId}")
    public ResponseEntity<Product> updateProductEndpoint(@RequestBody Product product, @PathVariable("id") long id, @PathVariable("productId") long productId){
        try{
            if(productService.getProductById(productId) != null) {
                productService.updateProduct(productId, product);
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        }catch (ProductException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
    }
}
