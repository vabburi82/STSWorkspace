package org.examples.springrest.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.examples.springrest.bean.Country;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@RestController
public class ContactController {

	private DBCollection userCollection;

	@PostConstruct
	public void init() {
		openmongoDBConnection();
	}

	private void openmongoDBConnection() {
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		DB db = mongoClient.getDB("testdev");
		userCollection = db.getCollection("users");
	}

	@RequestMapping(value = "/contacts", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Country> getCountries() {

		return loadAllusers();
	}

	@RequestMapping(value = "/contact/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public DBObject getCountryById(@PathVariable int id) {
		List<DBObject> listOfUsers = findUserByID(id);

		return listOfUsers.get(0);
	}

	private List<DBObject> findUserByID(int id) {
		List<DBObject> listOfUsers = new ArrayList<DBObject>();
		
		DBObject query = BasicDBObjectBuilder.start().add("_id", id).get();
		DBCursor cursor = userCollection.find(query);

		listOfUsers = cursor.toArray();
		return listOfUsers;
	}

	// Utiliy method to load user list.
	public List<Country> loadAllusers() {
		List<Country> listOfCountries = new ArrayList<Country>();
		DBCursor cursor = userCollection.find();
		for (DBObject dbObject : cursor) {
			Country user = new Country((Integer) dbObject.get("_id"), (String) dbObject.get("name"));
			listOfCountries.add(user);
		}
		return listOfCountries;
	}
}
