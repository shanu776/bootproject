package com.example.bootproject.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.bootproject.model.Employee;

public interface EmployeeDao extends CrudRepository<Employee, Integer>{
	
}
