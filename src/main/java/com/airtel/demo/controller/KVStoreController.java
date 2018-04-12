package com.airtel.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.airtel.demo.service.KVStoreService;
import com.airtel.demo.model.Person;

import ch.qos.logback.classic.Logger;


@RestController
@RequestMapping("/api/v1/")
public class KVStoreController {
	
	@Autowired
	private KVStoreService<String,Person> kvStoreService;
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(KVStoreController.class);
	
	
	@GetMapping("/persons")
	public List<Person> getAllPersons(){
		return kvStoreService.getAll();
	}
	
	@PostMapping("/persons/create")
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
		 kvStoreService.put(person.getId(),person);
		 return new ResponseEntity<>(person,HttpStatus.CREATED);
	}
	
	/*@Cacheable(value = "persons", key = "#id", unless = "#result == null")
	@GetMapping("/persons/{id}")
	public ResponseEntity<Object> getPerson(@PathVariable("id") String id) {
		return new ResponseEntity<>(kvStoreService.get(id),HttpStatus.OK);
		
	}*/
	
	@Cacheable(value = "person", key = "#id", unless = "#result == null")
	@GetMapping("/persons/{id}")
	public @ResponseBody Person getPerson(@PathVariable("id") String id) {
		return kvStoreService.get(id);
		
	}
	
	
	@GetMapping("/persons/count")
	public long getCount() {
		return kvStoreService.getCount();
	}
	
	@PutMapping("/persons/{id}")
	@CachePut(value = "person", key = "#id")
	public @ResponseBody Person updatePerson(@PathVariable("id") String id, @RequestBody Person person) {
		
		LOGGER.debug("id:  "+id);
		Person opt = kvStoreService.get(id);
			
		/*if(opt == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}*/
		
		person.setFirstName(person.getFirstName());
		person.setLastName(person.getLastName());
		person.setAge(person.getAge());
		
		kvStoreService.put(id,person);
		return person;
		
	}
	
	@DeleteMapping("/persons/{id}")
	@CacheEvict(value = "person", key = "#id")
	public ResponseEntity<String> deletePerson(@PathVariable("id") String id){	
		kvStoreService.delete(id);
		return new ResponseEntity<>("Person has been deleted !",HttpStatus.OK);
		
	}
	
	@DeleteMapping("/persons/delete")
	public ResponseEntity<String> deleteAllPersons(){
		
		kvStoreService.clear();
		return new ResponseEntity<>("All persons has been deleted !",HttpStatus.OK);
	}
	
	@GetMapping(value = "/deleteCache")
    @CacheEvict(value = "person", allEntries = true)
    public void deleteCache() {
        this.kvStoreService.deleteAllCache();
    }

	
}
