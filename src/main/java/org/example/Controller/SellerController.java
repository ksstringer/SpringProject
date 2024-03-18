package org.example.Controller;

import org.example.Exception.SellerException;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class SellerController {
    SellerService sellerService;
    @Autowired
    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }
    @GetMapping("/seller")
    public ResponseEntity<List<Seller>> getAllSellersEndpoint(){
        List<Seller> sellers = sellerService.getSellers();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }
    @PostMapping("/seller")
    public ResponseEntity<Object> postSellerEndpoint(@RequestBody Seller seller) throws SellerException {
        sellerService.addSeller(seller);
        try{
            return new ResponseEntity<>(seller, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("invalid seller post", HttpStatus.BAD_REQUEST);
        }
    }
}
