package com.airtel.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.airtel.demo.model.Person;

public interface KVStoreRepository extends MongoRepository<Person,String>{

}
