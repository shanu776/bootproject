package com.example.bootproject.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.bootproject.model.Product;

public interface ProductDao extends CrudRepository<Product, Integer>{
	
}
