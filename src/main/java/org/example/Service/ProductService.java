package org.example.Service;

import org.example.Exception.ProductException;
import org.example.Exception.ProductFormatException;
import org.example.Exception.ProductNotFoundException;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Repository.ProductRepository;
import org.example.Repository.SellerRepository;
import org.h2.jdbc.JdbcSQLDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;
    SellerRepository sellerRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, SellerRepository sellerRepository){
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        Main.log.info("New Product List created");
    }
    public List<Product> getProducts(){
        List<Product> productList = productRepository.findAll();
        Main.log.info("Product List returned: " + productList);
        return productList;
    }
    public void addProduct(long id, Product product) throws ProductException {
        Optional<Seller> optional = sellerRepository.findById(id);
        Seller seller;
        if(product.getName() == null || product.getName().isEmpty()){
            Main.log.warn("Product name is empty");
            throw new ProductException("Product name is empty");
        }
        if(product.getPrice() <= 0){
            Main.log.warn("Product price is less than or equal to 0");
            throw new ProductException("Product price is less than or equal to 0");
        }
        if(optional.isEmpty()){
            Main.log.warn("Seller with id " + id + " is not a verified Seller");
            throw new ProductException("Seller " + id + " is not a verified Seller");
        } else {
            seller = optional.get();
        }
        Product savedProduct = productRepository.save(product);
        seller.getProducts().add(savedProduct);
        sellerRepository.save(seller);
        Main.log.info("Product added: " + product.toString());
    }

    public Product getProductById(long id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }else{
            Product product = productOptional.get();
            Main.log.info("Product found: " + product);
            return productOptional.get();
        }
    }
    public void updateProduct(long id, Product product) throws ProductException {
        if(product.getName() == null || product.getName().isEmpty()){
            Main.log.warn("Product name is empty");
            throw new ProductFormatException("Product name is empty");
        }
        if(product.getPrice() <= 0){
            Main.log.warn("Product price is less than or equal to 0");
            throw new ProductFormatException("Product price is less than or equal to 0");
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            Product updatedproduct = productOptional.get();
            productRepository.save(updatedproduct);
            Main.log.info("Product updated. New values: " + product);
        } else {
            throw new ProductException("Invalid Seller");
        }
    }
    public void deleteProduct(long id){
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.delete(product);
            Main.log.info("Product deleted: " + id);
        }
    }
}
