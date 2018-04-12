package com.airtel.demo.configuration;

import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

import com.airtel.demo.controller.KVStoreController;

import ch.qos.logback.classic.Logger;

public class CustomCacheErrorHandler implements CacheErrorHandler {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(KVStoreController.class);
	
	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		LOGGER.error("Cache {} is down to clear with exception :{}", cache.getName(),
                exception.getMessage());
	}

	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache,Object key) {
		LOGGER.error("Cache {} is down to evict for key :{} with exception :{}",
                cache.getName(), key, exception.getMessage());

	}

	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		LOGGER.error("Cache {} is down to search for key :{} with exception :{}",
	                cache.getName(), key, exception.getMessage());

	}

	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key,Object value) {
		LOGGER.error("Cache {} is down to put for key :{} with exception :{}",
                cache.getName(), key, exception.getMessage());

	}

}
