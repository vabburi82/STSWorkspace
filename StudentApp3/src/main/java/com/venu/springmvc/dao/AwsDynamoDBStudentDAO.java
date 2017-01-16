package com.venu.springmvc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.ConsistentReads;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.venu.springmvc.domain.Student;


@Component
public class AwsDynamoDBStudentDAO {

  

	private static AmazonDynamoDBClient dynamoDB;
	private static DynamoDBMapper mapper;
   
    public  AwsDynamoDBStudentDAO() {

    	 
        String accessKey = "";
		String secretKey = "";
		dynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(accessKey,secretKey));
//    	dynamoDB = new AmazonDynamoDBClient();
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        dynamoDB.setRegion(usWest2);
        mapper = new DynamoDBMapper(dynamoDB);
        createTable();

    }

   

	private  void createTable()  {

        try {
            String tableName = "student";

            // Create a table with a primary hash key named 'name', which holds a string
            CreateTableRequest createTableRequest = new CreateTableRequest()
            		.withTableName(tableName)
            		.withKeySchema(new KeySchemaElement()
            							.withAttributeName("id")
            							.withKeyType(KeyType.HASH))
            		.withAttributeDefinitions(new AttributeDefinition()
            									.withAttributeName("id")
            									.withAttributeType(ScalarAttributeType.S))
            		.withProvisionedThroughput(new ProvisionedThroughput()
            									.withReadCapacityUnits(1L)
            									.withWriteCapacityUnits(1L));

            // Create table if it does not exist yet
            TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
            // wait for the table to move into ACTIVE state
            TableUtils.waitUntilActive(dynamoDB, tableName);

            // Describe our new table
            DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
            TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
            System.out.println("Table Description: " + tableDescription);
            

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	 public Student saveStudent(Student item) {
		 
		 mapper.save(item);
		 
		 return getById(item.getId());
		 
	 }
	 public Student updateStudent(String id, Student item) {
		
		Student stud = getById(id);	 
		if(null == stud) return stud;	 
		
		item.setId(id);
		mapper.save(item);
			 
		return getById(item.getId());
			 
		}
	 
	 public Student deleteStudent(String id) {
		 Student stud = getById(id, ConsistentReads.CONSISTENT.config());
		 
		 mapper.delete( stud );
		
		 return stud;
		 
	 }
	 
	 public List<Student> listStudent() {
		return mapper.scan(Student.class,  new DynamoDBScanExpression());
		
	 }
	 
	 
	 public List<Student> findByFirstName(String firstName) {
		
		 return filterByColumn(firstName, "firstName");
		 
	 }
	 public List<Student> findByLastName(String lastName) {
		 
		 return filterByColumn(lastName, "lastName");
		 
	 }

	 public List<Student> findByFirstNameBeginsWith(String firstName) {
		
		 return filterByColumnBeginsWith(firstName, "firstName");
		 
	 }
	 
	 public List<Student> findByLastNameBeginsWith(String lastName) {
		 
		 return filterByColumnBeginsWith(lastName, "lastName");
		 
	 }

	
	private List<Student> filterByColumnBeginsWith(String dataValue, String columnName) {
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":"+columnName, new AttributeValue().withS(dataValue));
		       
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
		    .withFilterExpression("begins_with("+ columnName +", :"+ columnName +")")
		    .withExpressionAttributeValues(eav);

		
		return mapper.scan(Student.class,  scanExpression);
	}


	private List<Student> filterByColumn(String dataValue, String columnName) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
			eav.put(":"+columnName, new AttributeValue().withS(dataValue));
	        
	        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
	            .withFilterExpression(columnName + " = :"+columnName )
	            .withExpressionAttributeValues(eav);
		 
		return mapper.scan(Student.class,  scanExpression);
	}
	
	public Student getById(String id, DynamoDBMapperConfig dynamoDBMapperConfig) {
		return mapper.load(Student.class, id, dynamoDBMapperConfig);
	}
	
	public Student getById(String id) {
		return mapper.load(Student.class, id);
	}

}
