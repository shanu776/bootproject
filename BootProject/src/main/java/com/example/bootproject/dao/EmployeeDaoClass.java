package com.example.bootproject.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootproject.model.Employee;
@Service
public class EmployeeDaoClass {

	@Autowired
	EmployeeDao employeeDao;
	
	public void save(Employee employee){
		employeeDao.save(employee);
	}
	
	
	public List<Employee> getEmployeeList(){
		return (List<Employee>)employeeDao.findAll();
	}
}
