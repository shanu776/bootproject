package com.example.bootproject.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.bootproject.model.Customer;

public interface CustomerDao extends CrudRepository<Customer, Integer>{
}
