package com.taskAssignment.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskAssignment.model.Student;
import com.taskAssignment.repository.StudentRepository;

@Service
@Transactional
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	public List<Student> listAllStudents() {
		return studentRepository.findAll();
	}

	public void saveStudent(Student student) {

		studentRepository.save(student);
	}

	public Student getStudent(int id) {
		return studentRepository.findById(id).get();
	}
	
	public Student getStudentbyEmail(String email) {
		return studentRepository.findByEmail(email);
	}

	public void deleteStudent(int id) {
		studentRepository.deleteById(id);
	}

}
