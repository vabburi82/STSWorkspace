package org.examples.springrest.dao;

import java.net.UnknownHostException;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * Java + MongoDB Hello world Example
 * 
 */
public class ContactDAO {
	private DB db;
	private DBCollection table;

	public void ContactDAO() {
		try {

			openConnection();
			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			table = db.getCollection("CONTACTS");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}

	public void findContactByName() {

		/**** Find and display ****/
		BasicDBObject searchQuery2 = new BasicDBObject().append("name", "Venu Abburi-updated");

		DBCursor cursor2 = table.find(searchQuery2);

		while (cursor2.hasNext()) {
			System.out.println(cursor2.next());
		}
	}

	public void updateContact() {
		/**** Update ****/
		// search document where name="mkyong" and update it with new values
		BasicDBObject query = new BasicDBObject();
		query.put("name", "Venu");

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", "Venu Abburi-updated");

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		table.update(query, updateObj);
	}

	public void loadAllContacts() {
		/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "Venu");

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	public void saveContact() {

		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject document = new BasicDBObject();
		document.put("name", "Venu");
		document.put("age", 30);
		document.put("createdDate", new Date());
		table.insert(document);

	}

	private void openConnection() throws UnknownHostException {
		/**** Connect to MongoDB ****/
		// Since 2.10.0, uses MongoClient
		MongoClient mongo = new MongoClient("localhost", 27017);

		/**** Get database ****/
		// if database doesn't exists, MongoDB will create it for you
		db = mongo.getDB("testdb");
	}
}
