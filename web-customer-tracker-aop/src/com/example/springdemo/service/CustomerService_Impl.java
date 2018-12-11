package com.example.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springdemo.dao.CustomerDAO;
import com.example.springdemo.entity.Customer;

@Service
public class CustomerService_Impl implements CustomerService {

	//inject - service depends on a dao
	@Autowired
	private CustomerDAO dao;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		
		return dao.getCustomers();
	}

	@Override
	@Transactional
	public void saveCustomer(Customer theCustomer) {
		
		dao.saveCustomer(theCustomer);
	}

	@Override
	@Transactional
	public Customer getCustomer(int theId) {
		
		return dao.getCustomer(theId);
	}

	@Override
	@Transactional
	public void deleteCustomer(int theId) {
		
		// delegate delete command to DAO
		dao.deleteCustomer(theId);
	}

	@Override
	@Transactional
	public List<Customer> searchCustomers(String theSearchName) {
		// 
		return dao.searchCustomers(theSearchName);
	}

	

}
