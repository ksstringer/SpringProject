package org.example.Service;

import org.example.Exception.ProductException;
import org.example.Exception.ProductFormatException;
import org.example.Exception.ProductNotFoundException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Repository.ProductRepository;
import org.example.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            //based on method of adding product to a specific seller (instead of indicating seller in request body),
            //this log/exception will not be thrown, but the controller BAD_REQUEST will be
            Main.log.warn("Seller with id " + id + " is not a verified Seller");
            throw new ProductException("Seller " + id + " is not a verified Seller");
        } else {
            seller = optional.get();
        }
        product.setSeller(seller);
        Product savedProduct = productRepository.save(product);
        seller.getProducts().add(savedProduct);
        sellerRepository.save(seller);
        Main.log.info("Product added: " + product.toString());
    }

    public Product getProductById(long id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            Main.log.info("Product found: " + product);
            return productOptional.get();
        }else{
            Main.log.warn("Product not found: " + id);
            throw new ProductNotFoundException("Product not found");
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
            updatedproduct.setName(product.getName());
            updatedproduct.setPrice(product.getPrice());
            productRepository.save(updatedproduct);
            Main.log.info("Product updated. New values: " + product);
        } else {
            //based on method of updating product for a specific seller (instead of indicating seller in request body),
            //this log/exception will not be thrown, but the controller BAD_REQUEST will be
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
