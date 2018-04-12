package com.airtel.demo.service;

import java.util.List;
import java.util.Optional;


public interface KVStoreService<K,V> {

	V get (K key);
	void put (K key, V value);
	void delete (K key);
	void clear ();
	long size ();
	
	List<V> getAll();
	long getCount();
	void deleteAllCache();
}
