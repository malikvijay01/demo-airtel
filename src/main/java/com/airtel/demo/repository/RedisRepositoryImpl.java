package com.airtel.demo.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.airtel.demo.model.Person;

@Repository
public class RedisRepositoryImpl implements RedisRepository{

	private static final String KEY = "person";
	
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Person> hashOperations;
    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    public void add(final Person person) {
        hashOperations.put(KEY, person.getId(), person);
    }

}
