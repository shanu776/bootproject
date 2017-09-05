package com.example.bootproject.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootproject.model.Customer;
@Service
public class CustomerDaoClass {

	@Autowired
	CustomerDao customerDao;
		
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerDao.save(customer);
	}
	
	public List<Customer> listCustomerData(){		
		return (List<Customer>) customerDao.findAll();		
	}
	
	public void deleteCustomerData(Customer customer){		
		customerDao.delete(customer);
	}
	
	public Customer getCustomerData(Integer id){
		return customerDao.findOne(id);		
	}
	
}