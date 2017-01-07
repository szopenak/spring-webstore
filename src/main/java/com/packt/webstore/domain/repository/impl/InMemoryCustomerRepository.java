package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;
@Repository
public class InMemoryCustomerRepository implements CustomerRepository {
	private List<Customer> listOfCustomers = new ArrayList<Customer>();
	public List<Customer> getAllCustomers() {
		listOfCustomers.add(new Customer("1", "Artur0", "Somewhere 11-2", 0));
		listOfCustomers.add(new Customer("2", "Chris", "Kickapoo 2-555", 0));
		// TODO Auto-generated method stub
		return listOfCustomers;
	}

}
