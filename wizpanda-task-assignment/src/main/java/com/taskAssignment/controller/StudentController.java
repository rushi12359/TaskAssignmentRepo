package com.taskAssignment.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskAssignment.model.AuthRequest;
import com.taskAssignment.model.JwtTokenResponse;
import com.taskAssignment.model.Student;
import com.taskAssignment.service.StudentService;
import com.taskAssignment.util.JwtUtil;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class StudentController {

	@Autowired
	StudentService studentService;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationmanager;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/students")
	public List<Student> listofStudents() {
		return studentService.listAllStudents();
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<Student> get(@PathVariable int id) {
		try {
			Student student = studentService.getStudent(id);
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/students/{email}")
	public ResponseEntity<Student> getemail(@PathVariable String email) {
		try {
			Student student = studentService.getStudentbyEmail(email);
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/student/create")
	public void create(@RequestBody Student student) {
		studentService.saveStudent(student);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> generateToken(@RequestBody AuthRequest authrequest) throws Exception {
		try {
			authenticationmanager.authenticate(
					new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));

		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authrequest.getUsername());
		final String token = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtTokenResponse(token));

	}

	@PutMapping("/student/{id}")
	public ResponseEntity<?> update(@RequestBody Student Student, @PathVariable int id) {
		try {
			Student existStudent = studentService.getStudent(id);
			Student.setId(id);
			studentService.saveStudent(Student);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/student/{id}")
	public void delete(@PathVariable int id) {

		studentService.deleteStudent(id);
	}

}
