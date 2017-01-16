package com.venu.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.venu.springmvc.dao.AwsDynamoDBStudentDAO;
import com.venu.springmvc.domain.Student;



@RestController
//@RequestMapping("/apiO")
public class StudentRestController {

	
	@Autowired
	private AwsDynamoDBStudentDAO awsStudentDAO;
	
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		return awsStudentDAO.listStudent();
	}
	
	@GetMapping("/students/findByFirstName/{firstName}")
	public List<Student> findStudentsByFirstName(@PathVariable("firstName") String firstName) {
		return awsStudentDAO.findByFirstName(firstName);
	}
	
	@GetMapping("/students/findByLastName/{lastName}")
	public List<Student> findStudentsByLastName(@PathVariable("lastName") String lastName) {
		return awsStudentDAO.findByLastName(lastName);
	}

	@GetMapping("/students/firstNameStartsWith/{firstName}")
	public List<Student> findStudentsByFirstNameStartsWith(@PathVariable("firstName") String firstName) {
		return awsStudentDAO.findByFirstNameBeginsWith(firstName);
	}
	
	@GetMapping("/students/lastNameStartsWith/{lastName}")
	public List<Student> findStudentsByLastNameStartsWith(@PathVariable("lastName") String lastName) {
		return awsStudentDAO.findByLastNameBeginsWith(lastName);
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Object> getStudent(@PathVariable("id") String id) {

		Student student = awsStudentDAO.getById(id);
		if (student == null) {
			return new ResponseEntity<Object>("No Student found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(student, HttpStatus.OK);
	}

	@PostMapping(value = "/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {

		awsStudentDAO.saveStudent(student);
		
		return new ResponseEntity<Object>(student, HttpStatus.OK);
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable String id) {

		if (null == awsStudentDAO.deleteStudent(id)) {
			return new ResponseEntity<Object>("No Student found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(id, HttpStatus.OK);

	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Object> updateStudent(@PathVariable String id, @RequestBody Student student) {
		
		student = awsStudentDAO.updateStudent(id,student);

		if (null == student) {
			return new ResponseEntity<Object>("No Student found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(student, HttpStatus.OK);
	}

}