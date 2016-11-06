package org.examples.springrest.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.examples.springrest.bean.Contact;
import org.examples.springrest.dao.ContactDAO3;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DBObject;

@RestController
public class ContactController2 {

	private ContactDAO3 contactsDao;

	@PostConstruct
	public void init() {
		contactsDao = new ContactDAO3();
	}

	@RequestMapping(value = "/v2/contacts", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Contact> getCountries() {

		List<Contact> allusers = contactsDao.loadAllusers();
		
		return allusers;
	}

	@RequestMapping(value = "/v2/contact/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public DBObject getCountryById(@PathVariable int id) {
		List<DBObject> listOfUsers = contactsDao.findUserByID(id);

		return listOfUsers.get(0);
	}

	
}
