package com.taskAssignment.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.taskAssignment.model.Student;

public interface StudentRepository extends JpaRepository<Student, Serializable> {
	
	@Query("select s FROM Student s WHERE s.email = :email")
	public Student findByEmail(String email);

}
