package com.example.springdemo.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springdemo.entity.Customer;
import com.example.springdemo.service.CustomerService;


@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//inject DAO into this controller
	@Autowired
	private CustomerService service;
	/**
	 * Retrieves a List of Customer objects from the DAO and holds
	 * these in a variable called "customerList" for the purpose
	 * of giving this List to a model as an attribute 
	 * @param theModel - used to pass a model to the JSP view page
	 * @return the name of the JSP view page
	 */
	@RequestMapping("/list")
	public String listCustomers(Model theModel) {
		
		//get Customers from DAO
		List<Customer> customerList = service.getCustomers();
		
		//add customers to MVC model
		theModel.addAttribute("customers",customerList);
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer
			(@ModelAttribute("customer") Customer theCustomer) {
		
		service.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate
				(@RequestParam("customerId") int theId,
										Model theModel) {
		//get Customer from the Service -> DAO -> DB
		Customer theCustomer = service.getCustomer(theId);
		//set Customer as the Model attribute -> populate form
		theModel.addAttribute("customer", theCustomer);
		//send to the form
		return "customer-form";
	}
	@GetMapping("/delete")
	public String deleteCustomer 
			(@RequestParam("customerId") int theId) {
		
		//delete the Customer  from Service -> DAO -> DB
		service.deleteCustomer(theId);
		//Redirect to a new Controller on completion of delete	
		return "redirect:/customer/list";
	}
	
	@PostMapping("/search")
	public String searchCustomers
			(@RequestParam("theSearchName") String theSearchName,
												Model theModel) {

		// search customers from the service
		List<Customer> theCustomers = 
				service.searchCustomers(theSearchName);
				
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);

		return "list-customers";		
	}

}










