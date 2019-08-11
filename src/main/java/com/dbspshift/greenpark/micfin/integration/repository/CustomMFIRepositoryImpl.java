package com.dbspshift.greenpark.micfin.integration.repository;

import com.dbspshift.greenpark.micfin.beans.MFI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Use this for complex MongoDB queries
 */

public class CustomMFIRepositoryImpl implements CustomMFIRepository<MFI,String>{

   /* @Autowired
    MFIRepository repository;*/

    @Override
    public Flux<MFI> findByNameStartingWith(String regexp) {
        /*Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^"+regexp));
        Flux<MFI> mfiList = repository.find(query,MFI.class);
       return mfiList;*/
        return null;
    }
}

