package com.Springboot.example.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Springboot.example.model.User;
import com.Springboot.example.repository.RoleRepository;
import com.Springboot.example.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@PersistenceContext
	private EntityManager em;
	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		System.out.println(email);
		User user = new User();
     try {
		 user= (User) em.createQuery("select u from User u where u.email =:email ")
				 .setParameter("email",email).getSingleResult();
	 }catch (Exception e){
     	return null;
	 }

		return user;
	}
}
