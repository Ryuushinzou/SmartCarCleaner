package com.scc.app.local.controllers;

import com.scc.app.firebase.models.User;
import com.scc.app.local.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll() {
		List<User> users = service.getAll();
		return ResponseEntity.ok(users);
	}

}
