package com.abburi.mongodb.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class MongoDBExample {

	public static void main(String[] args) {
		mongoOldMethods();
		mongoTestNewMethods();
		// mongoTestAuthentication();
	}

	public static void mongoOldMethods() {
		MongoClient mongoClient = null;

		try {
			System.out.println("Connecting using mongoOldMethods()...");
			mongoClient = new MongoClient();

			// Old way to get database - deprecated now
			DB db = mongoClient.getDB("test");

			DBCollection collection = db.getCollection("employee");
			System.out.println("collection: " + collection);

			System.out.println("Remove using BasicDBObjects...");
			BasicDBObject deleteQuery = new BasicDBObject();
			deleteQuery.put("_id", new BasicDBObject("$in", Arrays.asList("1", "2")));
			WriteResult removeresult = collection.remove(deleteQuery);
			System.out.println("Total records removed ..." + removeresult.getN());

			System.out.println("Inserting using BasicDBObjects...");
			final BasicDBObject basic1 = new BasicDBObject();
			basic1.put("_id", "1");
			basic1.put("type", "basic");
			basic1.put("first-name", "Amaury");
			basic1.put("last-name", "Valdes");
			collection.insert(basic1);
			System.out.println("Employee 1: " + basic1.toJson());

			final BasicDBObject basic2 = new BasicDBObject();
			basic2.put("_id", "2");
			basic2.put("type", "basic");
			basic2.put("first-name", "Jane");
			basic2.put("last-name", "Valdes");
			collection.insert(basic2);
			System.out.println("Employee 2: " + basic2.toJson());

			showAllDocs(collection);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
	}

	public static void mongoTestNewMethods() {
		MongoClient mongoClient = null;

		try {
			System.out.println("Connecting using mongoTestNewMethods() to 'test' database...");
			mongoClient = new MongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");

			MongoCollection<Document> collection = db.getCollection("employee");

			System.out.println("Remove using new Document...");
			Bson criteria = new Document("$in", Arrays.asList("101", "102"));
			Bson deleteQuery = new Document("_id", criteria);
			DeleteResult deleteResult = collection.deleteMany(deleteQuery);
			System.out.println("Total removed documents..." + deleteResult.getDeletedCount());

			System.out.println("Inserting using Map...");

			// ---Insert using Map Employee #1---
			final Map<String, Object> empMap1 = new HashMap<String, Object>();
			empMap1.put("_id", "101");
			empMap1.put("type", "map");
			empMap1.put("first-name", "Stephen");
			empMap1.put("last-name", "Murphy");

			System.out.println("Employee: 101" + empMap1);
			collection.insertOne(new Document(empMap1));

			// ---Insert using Map Employee #2---
			final Map<String, Object> empMap2 = new HashMap<String, Object>();
			empMap2.put("_id", "102");
			empMap2.put("type", "map");
			empMap2.put("first-name", "Harold");
			empMap2.put("last-name", "Jansen");

			System.out.println("Employee: 102" + empMap2);
			collection.insertOne(new Document(empMap2));

			// Show all documents in the collection
			showAllDocuments(collection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
	}

	public static void mongoTestAuthentication() {
		String jsonString;
		Document doc;
		ObjectMapper mapper;
		MongoClient mongoClient = null;

		try {
			System.out.println("Connecting using mongoTestAuthentication() to 'secured' database...");
			MongoClientURI uri = new MongoClientURI("mongodb://root:root123@localhost:27017/?authSource=admin");
			mongoClient = new MongoClient(uri);

			MongoDatabase db = mongoClient.getDatabase("secured");

			MongoCollection<Document> collection = db.getCollection("employee");
			System.out.println("Inserting using Jackson Object->JSON...");

			// ---Employee #1---
			final Employee employee1 = new Employee();
			employee1.setEmployeeId("1001");
			employee1.setType("json");
			employee1.setFirstName("Jacob");
			employee1.setLastName("Matthews");

			// Use Jackson to convert Object to JSON String
			mapper = new ObjectMapper();
			jsonString = mapper.writeValueAsString(employee1);

			// Insert JSON into MongoDB
			System.out.println("Employee #1001: " + jsonString);
			doc = Document.parse(jsonString);
			collection.insertOne(doc);

			// ---Employee #2---
			final Employee employee2 = new Employee();
			employee2.setEmployeeId("1002");
			employee2.setType("json");
			employee2.setFirstName("Allison");
			employee2.setLastName("Jones");

			// Use Jackson to convert Object to JSON String
			mapper = new ObjectMapper();
			jsonString = mapper.writeValueAsString(employee2);

			// Insert JSON into MongoDB
			System.out.println("Employee #1002: " + jsonString);
			doc = Document.parse(jsonString);
			collection.insertOne(doc);

			// ---Employee #3---
			final Employee employee3 = new Employee();
			employee3.setEmployeeId("1003");
			employee3.setType("json");
			employee3.setFirstName("Debbie");
			employee3.setLastName("Richards");

			// Use Jackson to convert Object to JSON String
			mapper = new ObjectMapper();
			jsonString = mapper.writeValueAsString(employee3);

			// Insert JSON into MongoDB
			System.out.println("Employee #1003: " + jsonString);
			doc = Document.parse(jsonString);
			collection.insertOne(doc);

			// Show all documents in the collection
			showAllDocuments(collection);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
	}

	public static void showAllDocuments(final MongoCollection<Document> collection) {

		System.out.println("----[Retrieve All Documents in Collection]----");
		for (Document doc : collection.find()) {
			System.out.println(doc.toJson());
		}
	}

	public static void showAllDocs(final DBCollection collection) {
		DBCursor cursor = (DBCursor) collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next().toString());
			}
		} finally {
			cursor.close();
		}
	}
}