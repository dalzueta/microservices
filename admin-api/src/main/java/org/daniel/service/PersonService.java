package org.daniel.service;

import org.daniel.model.Person;
import org.daniel.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

  @Autowired
  private PersonRepository repository;

  public List<Person> getAll() {
    return repository.findAll();
  }

  public Person add(Person person) {
    return repository.save(person);
  }
}
