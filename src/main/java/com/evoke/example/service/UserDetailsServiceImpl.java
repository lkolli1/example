package com.evoke.example.service;


import com.evoke.example.entities.User;
import com.evoke.example.model.UserDetailsImpl;
import com.evoke.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException 
	{
		//loading model class user object
		User user = repository.findByUserName(username)
				.orElseThrow(()->new UsernameNotFoundException("User not exist" + username));
		//converting into Spring Security User object
		return UserDetailsImpl.build(user);
	}

}
