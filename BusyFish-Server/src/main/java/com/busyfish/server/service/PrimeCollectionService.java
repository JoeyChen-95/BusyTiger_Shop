package com.busyfish.server.service;

import com.busyfish.server.exception.ItemException.ItemException;
import com.busyfish.server.exception.PrimeCollectionException.ErrorCode;
import com.busyfish.server.exception.PrimeCollectionException.PrimeCollectionException;
import com.busyfish.server.exception.UserException.UserException;
import com.busyfish.server.mapper.OrderMapper;
import com.busyfish.server.model.Item;
import com.busyfish.server.model.ItemEnum.ItemOverviewResponse;
import com.busyfish.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * author:junjian.chen
 * 下午5:03
 */
@Service("PrimeCollectionService")
public class PrimeCollectionService {

    @Autowired
    ItemService itemService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Transactional
    public void createPrimeCollection(String name){
        if(name==null||name.trim().length()==0){
            throw new PrimeCollectionException(ErrorCode.EMPTY_COLLECTION_NAME,"Name cannot be empty!");
        }

        String primeCollectionListKey=String.format("prime_collection_"+name);


        if (redisTemplate.hasKey(primeCollectionListKey)) {
            throw new PrimeCollectionException(ErrorCode.DUPLICATE_COLLECTION_NAME,"A collection with this name already exists!");
        } else {
            redisTemplate.boundSetOps(primeCollectionListKey).add("0");
            redisTemplate.boundSetOps("prime_collection").add(name);
        }
    }

    @Transactional
    public void deletePrimeCollection(String name){
        String primeCollectionListKey=String.format("prime_collection_"+name);
        redisTemplate.delete(primeCollectionListKey);
        redisTemplate.boundSetOps("prime_collection").remove(name);
    }

    @Transactional
    public void addItemToPrimeCollection(String collectionName, Integer itemId){
        String primeCollectionListKey=String.format("prime_collection_"+collectionName);
        if(!redisTemplate.hasKey(primeCollectionListKey)){
            throw new PrimeCollectionException(ErrorCode.NO_EXISTING_COLLECTION,String.format("The collection %s does not exist!",collectionName));
        }
        if(itemService.selectItemById(itemId)==null){
            throw new ItemException(com.busyfish.server.exception.ItemException.ErrorCode.NO_EXISTING_ITEM,"Th item does not exist!");
        }

        redisTemplate.boundSetOps(primeCollectionListKey).add(String.valueOf(itemId));
    }

    @Transactional
    public void deleteItemFromPrimeCollection(String collectionName, Integer itemId){
        String primeCollectionListKey=String.format("prime_collection_"+collectionName);
        if(!redisTemplate.hasKey(primeCollectionListKey)){
            throw new PrimeCollectionException(ErrorCode.NO_EXISTING_COLLECTION,String.format("The collection %s does not exist!",collectionName));
        }
        redisTemplate.boundSetOps(primeCollectionListKey).remove(itemId.toString());
    }

    @Transactional
    public List<Item> selectAllItemsOfPrimeCollection(String collectionName){
        String primeCollectionListKey=String.format("prime_collection_"+collectionName);
        //Check if the prime collection exists
        if(!redisTemplate.hasKey(primeCollectionListKey)){
            throw new PrimeCollectionException(ErrorCode.NO_EXISTING_COLLECTION,String.format("The collection %s does not exist!",collectionName));
        }
        //Get a list of itemId in a collection from Redis
        List<Integer> collectionList=new ArrayList<>();
        for(String s:redisTemplate.boundSetOps(primeCollectionListKey).members()){
            collectionList.add(Integer.valueOf(s));
        }
        collectionList.remove(0);
        //Query information of those items from the itemId list
        List<Item> itemList=new ArrayList<>();
        for(Integer itemId:collectionList){
            Item item=itemService.selectItemById(itemId);
            if(item!=null){
                itemList.add(item);
            }
        }
        return itemList;
    }

    @Transactional
    public List<String> selectAllCollections(){
        if(!redisTemplate.hasKey("prime_collection")){
            return null;
        }else{
            return new ArrayList<>(redisTemplate.boundSetOps("prime_collection").members());
        }
    }

}
