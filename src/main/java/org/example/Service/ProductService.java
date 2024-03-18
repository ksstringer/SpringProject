package org.example.Service;

import org.example.Exception.ProductException;
import org.example.Exception.ProductFormatException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Repository.ProductRepository;
import org.h2.jdbc.JdbcSQLDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;
    public SellerService sellerService;
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
        Main.log.info("New Product List created");
    }
    public List<Product> getProducts(){
        List<Product> productList = productRepository.findAll();
        Main.log.info("Product List returned: " + productList);
        return productList;
    }
    public void addProduct(Product product) throws ProductException {
        //when Product is created, set the ID to a randomized value
        product.setId((int) (Math.random() * 100000000));
        int sellerId = product.getSeller();
        if(product.getName() == null || product.getName().isEmpty()){
            Main.log.warn("Product name is empty");
            throw new ProductException("Product name is empty");
        }
        if(product.getPrice() <= 0){
            Main.log.warn("Product price is less than or equal to 0");
            throw new ProductException("Product price is less than or equal to 0");
        }
        if(!sellerService.isVerifiedSeller(sellerId)){
            Main.log.warn("Seller with id " + sellerId + " is not a verified Seller");
            throw new ProductException("Seller " + sellerId + " is not a verified Seller");
        }
        //how to fix this from always being null
        //old version: (productDAO.getProductById(product.getId()) == null)
        if(productRepository.findById(product.getId()) == null){
            productRepository.save(product);
            Main.log.info("Product added: " + product.toString());
        }
    }
    public Product getProductById(int id){
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.get();
        Main.log.info("Product found: " + product);
        return productOptional.get();
    }
    public Product updateProduct(int id, Product product) throws ProductException {
        if(product.getName() == null || product.getName().isEmpty()){
            Main.log.warn("Product name is empty");
            throw new ProductFormatException("Product name is empty");
        }
        if(product.getPrice() <= 0){
            Main.log.warn("Product price is less than or equal to 0");
            throw new ProductFormatException("Product price is less than or equal to 0");
        }
        try {
            //original: productDAO.updateProduct(product);
            Optional<Product> productOptional = productRepository.findById(id);
            Product updatedproduct = productOptional.get();
            productRepository.save(updatedproduct);
            Main.log.info("Product updated. New values: " + product);
        //how to make this throw??
        }catch (JdbcSQLDataException e){
            e.printStackTrace();
            throw new ProductException("Invalid Seller");
        }
        return product;
    }
    public void deleteProduct(int id){
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.get();
        productRepository.delete(product);
        Main.log.info("Product deleted: " + id);
    }
}
