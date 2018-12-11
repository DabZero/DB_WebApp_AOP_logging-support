package com.example.springdemo.dao;

import java.util.List;




import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.example.springdemo.entity.Customer;
/**
 * @Repository lets Spring know we are registering this class
 * This class implements our DAO Interface & overrides its only method
 * Connects and talks with our DB 
 */
@Repository
public class CustomerDAO_Impl implements CustomerDAO {
	
	// inject sessionFactory to connect + talk w/ DB
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Opens a session to query the DB 
	 * @return customers is a List of Customers from the DB
	 */
	//@Override
	public List<Customer> getCustomers() {
		 
		//get a Hibernate session
		Session session = sessionFactory.getCurrentSession();
		
		//create a query and order by Java property name
		Query<Customer> theQuery = 
			session.createQuery("FROM Customer ORDER BY lastName", Customer.class);
		
		//execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		//return the customers that you retrieve
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get current Hibernate sesssion
		Session session = sessionFactory.getCurrentSession();
		//save the New -or- Update the existing Customer to DB
		session.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		//  Get current Hibernate sesssion
		Session session = sessionFactory.getCurrentSession();
		//  Read/Retrieve data using PK(id)
		Customer customer = session.get(Customer.class, theId);
		//  return the object	
		return customer;
	}

	@Override
	public void deleteCustomer(int theId) {
		//  Get current Hibernate sesssion
		Session session = sessionFactory.getCurrentSession();
		//delete object with primary key
		Query theQuery = session.createQuery
										//	Key : Value as a param
				("DELETE FROM Customer WHERE id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		//General purpose command "Do the work" update/del/etc...
		theQuery.executeUpdate();		
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// get the current hibernate session
			Session session = sessionFactory.getCurrentSession();
				
		//Default is empty unless True -> Update this value
			Query theQuery = null;
				
		// only search by name if theSearchName is not empty
			if (theSearchName != null && theSearchName.trim().length() > 0) {

			// search for firstName or lastName ... case insensitive
				theQuery =session.createQuery
						("FROM Customer WHERE lower(firstName) "
						+ "like :theName  OR  lower(lastName) "
						+ "like :theName", Customer.class);
				theQuery.setParameter("theName", 
						"%" + theSearchName.toLowerCase() + "%");
				}
			else {
			// theSearchName is empty ... so just get all customers
				theQuery =session.createQuery
					("from Customer", Customer.class);			
				}
				
			// execute query and get result list (Object/s)
				List<Customer> customers = theQuery.getResultList();
						
				// return the results		
				return customers;
	}

}
