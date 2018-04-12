package com.airtel.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airtel.demo.model.Person;
import com.airtel.demo.repository.KVStoreRepository;
import com.airtel.demo.repository.RedisRepository;

@Service
public class KVStoreServiceImpl<K, V> implements KVStoreService<K, V> {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(KVStoreServiceImpl.class);

	@Autowired
	KVStoreRepository kvStoreRepository;
	@Autowired
	RedisRepository redisRepository;

	@Override
	public void put(K key, V value) {

		Person person = (Person) value;
		redisRepository.add(person);
		kvStoreRepository.save(person);

	}

	@Override
	public void delete(K key) {
		kvStoreRepository.deleteById((String) key);
	}

	@Override
	public void clear() {
		kvStoreRepository.deleteAll();

	}

	@Override
	public long size() {
		return kvStoreRepository.findAll().size();
	}

	@Override
	public V get(K key) {
		V person = (V) kvStoreRepository.findById(key.toString()).orElse(null);
		return (V) person;
	}

	@Override
	public List<V> getAll() {
		return (List<V>) kvStoreRepository.findAll();
	}

	@Override
	public long getCount() {
		return kvStoreRepository.count();
	}

	@Override
	public void deleteAllCache() {
		LOGGER.info("Cached deleted");

	}

}
