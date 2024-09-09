package org.daniel.controller;

import org.daniel.model.Person;
import org.daniel.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

  @Autowired
  private PersonService service;

  @GetMapping
  public ResponseEntity<List<Person>> getUsers() {
    List<Person> users = service.getAll();
    return ResponseEntity.ok(users);
  }

  @PostMapping
  public ResponseEntity<Person> addUser(@RequestBody Person person) {
    Person newUser = service.add(person);
    return ResponseEntity.ok(newUser);
  }
}
