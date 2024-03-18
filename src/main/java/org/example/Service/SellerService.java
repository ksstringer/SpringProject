package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Seller;
import org.example.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    SellerRepository sellerRepository;
    @Autowired
    public SellerService(SellerRepository sellerRepository){
        this.sellerRepository = sellerRepository;
        Main.log.info("New Seller List created");
    }
    public List<Seller> getSellers(){
        List<Seller> sellerList = sellerRepository.findAll();
        Main.log.info("Seller List returned: " + sellerList);
        return sellerList;
    }
    public Seller getSellerById(int id) {
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        Seller seller = sellerOptional.get();
        Main.log.info("Seller found: " + seller);
        return sellerOptional.get();
    }
    public void addSeller(Seller seller) throws SellerException {
        if(seller.getName().isEmpty()){
            Main.log.warn("Seller name is empty");
            throw new SellerException("Seller name is empty");
        }
        try{
            this.sellerRepository.save(seller);
            //how to connect try-catch block with spring?
        }catch (SellerException e){
            Main.log.warn(e.getMessage());
            throw new SellerException(e.getMessage());
        }
    }
    public boolean isVerifiedSeller(int sellerId){
        //figure out how to replace DAO query and calculation of true/false for whether the id exists or not
        //required for check in Product Service
        return false;
    }
}
