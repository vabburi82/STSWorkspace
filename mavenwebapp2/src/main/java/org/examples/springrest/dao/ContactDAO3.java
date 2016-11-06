package org.examples.springrest.dao;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.examples.springrest.bean.Contact;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Repository
public class ContactDAO3 {

	private DBCollection userCollection;

	public ContactDAO3() {
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

	

	public List<DBObject> findUserByID(int id) {
		List<DBObject> listOfUsers = new ArrayList<DBObject>();
		
		DBObject query = BasicDBObjectBuilder.start().add("_id", id).get();
		DBCursor cursor = userCollection.find(query);

		listOfUsers = cursor.toArray();
		return listOfUsers;
	}

	// Utiliy method to load user list.
	public List<Contact> loadAllusers() {
		List<Contact> listOfUsers = new ArrayList<Contact>();
		DBCursor cursor = userCollection.find();
		for (DBObject dbObject : cursor) {
			System.out.println(dbObject);
			Contact user = new Contact((Integer) dbObject.get("_id"), (String) dbObject.get("name"));
			listOfUsers.add(user);
		}
		return listOfUsers;
	}
}
