package com.taskAssignment.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskAssignment.model.Student;
import com.taskAssignment.repository.StudentRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student=studentRepository.findByEmail(username);
		
		return new org.springframework.security.core.userdetails.User(student.getEmail(), student.getPassword(), new ArrayList<>());
	}

}
