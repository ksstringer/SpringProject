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
    public Seller getSellerById(long id) throws SellerException {
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if(sellerOptional.isEmpty()) {
            Main.log.warn("Seller id " +id+ "does not exist");
            throw new SellerException("Seller id " +id+ "does not exist");
        }else{
            Seller seller = sellerOptional.get();
            Main.log.info("Seller found: " + seller);
            return sellerOptional.get();
        }
    }
    public void addSeller(Seller seller) throws SellerException {
        if(seller.getName().isEmpty()){
            Main.log.warn("Seller name is empty");
            throw new SellerException("Seller name is empty");
        }
        else{
            this.sellerRepository.save(seller);
        }
    }
}
