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

import com.venu.springmvc.dao.StudentDAO;
import com.venu.springmvc.domain.Student;



@RestController
public class StudentRestController {

	
	@Autowired
	private StudentDAO studentDAO;
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		return studentDAO.list();
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Object> getStudent(@PathVariable("id") Long id) {

		Student student = studentDAO.get(id);
		if (student == null) {
			return new ResponseEntity<Object>("No Student found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(student, HttpStatus.OK);
	}

	@PostMapping(value = "/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {

		studentDAO.create(student);

		return new ResponseEntity<Object>(student, HttpStatus.OK);
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {

		if (null == studentDAO.delete(id)) {
			return new ResponseEntity<Object>("No Student found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(id, HttpStatus.OK);

	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Object> updateStudent(@PathVariable Long id, @RequestBody Student student) {

		student = studentDAO.update(id, student);

		if (null == student) {
			return new ResponseEntity<Object>("No Student found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(student, HttpStatus.OK);
	}

}