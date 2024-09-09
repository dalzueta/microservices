package org.daniel.controller;

import java.util.List;
import org.daniel.model.Person;
import org.daniel.model.User;
import org.daniel.service.PersonService;
import org.daniel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping
  public ResponseEntity<List<User>> getAll() {
    List<User> users = service.getAll();
    return ResponseEntity.ok(users);
  }

  // Endpoint para agregar un nuevo usuario
  @PostMapping
  public ResponseEntity<User> add(@RequestBody User user) {
    User newUser = service.add(user);
    return ResponseEntity.ok(newUser);
  }
}
