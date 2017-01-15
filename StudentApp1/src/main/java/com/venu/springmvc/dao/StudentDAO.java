package com.venu.springmvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.venu.springmvc.domain.Student;


@Component
public class StudentDAO {

	// Dummy database. Initialize with some dummy values.
	private static List<Student> students;
	{
		students = new ArrayList<Student>();
		students.add(new Student(101, "John", "Doe", "djohn@gmail.com", "121-232-3435"));
		students.add(new Student(201, "Russ", "Smith", "sruss@gmail.com", "343-545-2345"));
		students.add(new Student(301, "Kate", "Williams", "kwilliams@gmail.com", "876-237-2987"));
		students.add(new Student(System.currentTimeMillis(), "Venu", "Abburi", "vabburi@gmail.com", "508-816-7920"));
	}

	/**
	 * Returns list of students from dummy database.
	 * 
	 * @return list of students
	 */
	public List<Student> list() {
		return students;
	}

	/**
	 * Return student object for given id from dummy database. If student is
	 * not found for id, returns null.
	 * 
	 * @param id
	 *            student id
	 * @return student object for given id
	 */
	public Student get(Long id) {

		for (Student c : students) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Create new student in dummy database. Updates the id and insert new
	 * student in list.
	 * 
	 * @param student
	 *            Student object
	 * @return student object with updated id
	 */
	public Student create(Student student) {
		student.setId(System.currentTimeMillis());
		students.add(student);
		return student;
	}

	/**
	 * Delete the student object from dummy database. If student not found for
	 * given id, returns null.
	 * 
	 * @param id
	 *            the student id
	 * @return id of deleted student object
	 */
	public Long delete(Long id) {

		for (Student c : students) {
			if (c.getId().equals(id)) {
				students.remove(c);
				return id;
			}
		}

		return null;
	}

	/**
	 * Update the student object for given id in dummy database. If student
	 * not exists, returns null
	 * 
	 * @param id
	 * @param student
	 * @return student object with id
	 */
	public Student update(Long id, Student student) {

		for (Student c : students) {
			if (c.getId().equals(id)) {
				student.setId(c.getId());
				students.remove(c);
				students.add(student);
				return student;
			}
		}

		return null;
	}

}