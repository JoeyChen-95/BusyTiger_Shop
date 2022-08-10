package com.busyfish.server.controller;

import com.busyfish.server.model.Item;
import com.busyfish.server.model.Order;
import com.busyfish.server.service.PrimeCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author:junjian.chen
 * 上午10:58
 */
@RestController
@Slf4j
@RequestMapping("/collection")
public class PrimeCollectionController {
    @Autowired
    PrimeCollectionService primeCollectionService;

    @GetMapping(value = "/selectCollection")
    public List<Item> selectCollection(@RequestParam("collectionName") String collectionName) {
        try{
            return primeCollectionService.selectAllItemsOfPrimeCollection(collectionName);
        }catch (Exception e){
            return null;
        }
    }

    @PostMapping(value = "/insertCollection")
    public ResponseBody insertCollection(@RequestParam("collectionName") String collectionName){
        try{
            primeCollectionService.createPrimeCollection(collectionName);
            return new ResponseBody(200,"Create collection successfully!");
        }catch (Exception e){
            return new ResponseBody(500,e.getMessage());
        }
    }

    @PostMapping(value = "/addItemToCollection")
    public ResponseBody addItemToCollection(@RequestParam("collectionName") String collectionName,
                                            @RequestParam("itemId") Integer itemId){
        try{
            primeCollectionService.addItemToPrimeCollection(collectionName, itemId);
            return new ResponseBody(200,String.format("Add item %s to collection successfully!",itemId.toString()));
        }catch (Exception e){
            return new ResponseBody(500,e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteCollection")
    public ResponseBody deleteCollection(@RequestParam("collectionName") String collectionName){
        try{
            primeCollectionService.deletePrimeCollection(collectionName);
            return new ResponseBody(200,"Delete collection successfully");
        }catch (Exception e){
            return new ResponseBody(500,e.getMessage());
        }
    }

    @PostMapping(value = "/deleteItemFromCollection")
    public ResponseBody deleteItemFromCollection(@RequestParam("collectionName") String collectionName,
                                                 @RequestParam("itemId") Integer itemId){
        try{
            primeCollectionService.deleteItemFromPrimeCollection(collectionName, itemId);
            return new ResponseBody(200,String.format("Delete item %s from collection successfully!",itemId.toString()));
        }catch (Exception e){
            return new ResponseBody(500,e.getMessage());
        }
    }

    @GetMapping(value = "/selectAllCollection")
    public List<String> selectAllCollection(){
        try{
            return primeCollectionService.selectAllCollections();
        }catch (Exception e){
            return null;
        }
    }


}
