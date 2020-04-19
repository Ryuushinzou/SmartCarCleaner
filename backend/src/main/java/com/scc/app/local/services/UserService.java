package com.scc.app.local.services;

import com.scc.app.firebase.models.User;
import com.scc.app.local.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public List<User> getAll() {
		return userRepo.findAll();
	}
}
